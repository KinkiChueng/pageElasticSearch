package com.gafis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

/**
 * Created by lasia on 2017/9/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlServiceTest {
    @Autowired
    SqlService sqlService;


    @Test
    public void putInListTest() throws SQLException {
        String textAreaSql = "SELECT max(seq) from GAFIS_PERSON";
        sqlService.putInList(textAreaSql);
    }

    @Test
    public void execSqlTest() throws SQLException {
        String textAreaSql = "SELECT max(seq) from GAFIS_PERSON;SELECT max(seq) from GAFIS_PERSON;";
        sqlService.execSql(textAreaSql);
    }

    @Test
    public void getMaxSeqTest() throws SQLException {
        String textAreaSql = "SELECT max(seq) from GAFIS_PERSON";
        System.out.println(sqlService.getMaxSeq(textAreaSql));
    }

    @Test
    public void getInfoTest() throws SQLException {
        String textAreaSql = "SELECT * from GAFIS_PERSON";
        System.out.println(sqlService.getInfo(textAreaSql, 1016619L));
    }
}
