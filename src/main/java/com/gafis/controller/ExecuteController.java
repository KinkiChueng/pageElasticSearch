package com.gafis.controller;

import com.gafis.service.SqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by lasia on 2017/9/5.
 */
@RestController
@RequestMapping("/get")
public class ExecuteController {
    @Autowired
    SqlService sqlService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/sql")
    public String getSql(HttpServletRequest request) throws SQLException {
        String textAreaSql = request.getParameter("sql");
        try {
            sqlService.execSql(textAreaSql);
        } catch (SQLException e) {
            logger.error("执行语句失败\n" + e.getMessage());
            return "{\"message\": \""+e.getMessage()+"\"}";
        }
        logger.info(textAreaSql + " 处理成功");
        return "{\"message\": \"success\"}";
    }
}
