package com.gafis.mapper;


import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * Created by lasia on 2017/9/5.
 */
public interface ServiceMapper {

    /**
     * sql入库
     * @param uuid
     * @param name
     * @param sql
     * @param createtime
     * @param flag
     * @param version
     * @param seq
     */
    void putInList(@Param("uuid")String uuid, @Param("name")String name, @Param("sql")String sql, @Param("createtime")Timestamp createtime, @Param("flag")int flag, @Param("version")int version, @Param("seq")Long seq);

    /**
     * 获取当前最大seq
     * @param sql
     * @return
     * @throws Exception
     */
    long getMaxSeq(@Param("sql")String sql);

    /**
     * 通过传入sql seq获取信息
     * @param infoSql
     * @param maxSeq
     * @return
     */
    List<Map<String,Object>> selectInfoBySeq(@Param("infoSql")String infoSql, @Param("maxSeq")long maxSeq);

}
