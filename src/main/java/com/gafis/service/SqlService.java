package com.gafis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lasia on 2017/9/5.
 */

public interface SqlService {
    /**
     *  将sql语句入库
     * @param textAreaSql
     * @throws SQLException
     */
    void putInList(String textAreaSql) throws SQLException;

    /**
     * 切分当前用户输入的sql，执行调用
     *  @param textAreaSql
     * @throws SQLException
     */
    void execSql(String textAreaSql) throws SQLException;

    /**
     * 获取当前最大seq
     * @param maxSql
     * @return
     */
    Long getMaxSeq(String maxSql) throws SQLException;

    /**
     * 获取sql查询的信息
     * @param infoSql
     * @param maxSeq
     * @return
     */
    List<Map<String, Object>> getInfo(String infoSql, Long maxSeq);

    /**
     * 创建uuid
     * @return
     */
    String createUuid();

    /**
     * 切割获取名称
     * @param textAreaSql
     * @return
     */
    String cutSqlForName(String textAreaSql) throws SQLException;

    /**
     * 创建空索引
     */
    void indexElastic();

    /**
     *  建elasticsearch索引
     */
    void reportElastic();

    /**
     * 更新seq,标记位flag
     */
}
