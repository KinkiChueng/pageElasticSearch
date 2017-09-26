package com.gafis.service;


import com.gafis.mapper.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lasia on 2017/9/5.
 */
@Service
public class SqlServiceImpl implements SqlService {

    @Autowired
    private ServiceMapper serviceMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 将sql语句入库
     *
     * @param textAreaSql
     * @throws SQLException
     */
    @Override
    public void putInList(String textAreaSql) throws SQLException {
        serviceMapper.putInList(createUuid(), cutSqlForName(textAreaSql), textAreaSql, new Timestamp(System.currentTimeMillis()), 0, 1, 0L);
        logger.info("该条sql语句入库成功");
    }

    /**
     * 切分当前用户输入的sql，执行调用
     *
     * @param textAreaSql
     * @throws SQLException
     */
    @Override
    public void execSql(String textAreaSql) throws SQLException {
        if (null != textAreaSql) {
            String[] inputSqls = textAreaSql.split(";", -1);
            int length = textAreaSql.endsWith(";") ? inputSqls.length - 1 : inputSqls.length;       //因为有的输入sql，第二句结尾没有分号；有的可能有，这样切割的字符串数组长度会有影响
            int selectNum = textAreaSql.toLowerCase().indexOf("select");
            //只有包含两个sql语句（查询语句，最大值语句）的输入，才入库，才创建索引
            if (length == 2) {
                try {
                    putInList(textAreaSql);
                    Long maxSeq = getMaxSeq(inputSqls[1]);

                    logger.info("成功获取最大值：" + maxSeq);
                    List<Map<String, Object>> infoList = getInfo(inputSqls[0], maxSeq);
                    logger.info("成功获取查询信息列表");
                } catch (SQLException e) {
                    throw new SQLException(e);
                }
            } else {
                throw new SQLException("输入sql格式不对,请连续输入两条sql语句");
            }
        }
    }

    /**
     * 获取当前最大seq
     *
     * @param maxSql
     * @return
     */
    @Override
    public Long getMaxSeq(String maxSql) throws SQLException {
        if (maxSql.toLowerCase().contains("max") || maxSql.contains("MAX")) {
            Long maxSeq = serviceMapper.getMaxSeq(maxSql.trim());
            return maxSeq;
        } else {
            throw new SQLException("第二条sql语句不是查询最大值的sql");
        }
    }

    /**
     * 获取sql查询的信息
     *
     * @param infoSql
     * @param maxSeq
     * @return
     */
    @Override
    public List<Map<String, Object>> getInfo(String infoSql, Long maxSeq) {
        List<Map<String, Object>> infoList = serviceMapper.selectInfoBySeq(infoSql, maxSeq);
        return infoList;
    }

    /**
     * 创建空索引
     */
    @Override
    public void indexElastic() {

    }

    /**
     * 建elasticsearch索引
     */
    @Override
    public void reportElastic() {

    }

    /**
     * 创建uuid
     *
     * @return
     */
    @Override
    public String createUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    /**
     * 切割获取名称
     * 将两句sql语句的主表表名切割出来，比较。用第二条的长度为准，切第一条的表名
     * @param textAreaSql
     * @return
     */
    @Override
    public String cutSqlForName(String textAreaSql) throws SQLException {
        int semicolonPlace = textAreaSql.indexOf(";");
        int firstFromPlace = textAreaSql.lastIndexOf("from", semicolonPlace);
        int lastFromPlace = textAreaSql.lastIndexOf("from");
        String secSqlname = textAreaSql.substring(lastFromPlace + 5).trim();
        String firSqlname = textAreaSql.substring(firstFromPlace + 5, firstFromPlace + 5 +secSqlname.length()).trim();

        if (firSqlname.equals(secSqlname)) {
            return firSqlname;
        } else {
            throw new SQLException("请检查您的SQL语句，两条SQL语句必须操作在同一张表");
        }
    }
}
