package com.example.web.controller;

import com.example.web.Application;
import com.example.web.base.WebResult;
import com.example.web.dto.QueryDto;
import org.jason.datapermissioncheck.DataPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/customerList")
    @ResponseBody
    public WebResult customerList(String token) {
        Integer userId = (Integer) Application.redisMap.get(token).get("userId");
        return WebResult.successWebResult(jdbcTemplate.queryForList("select * from t_customer where user_id=?", userId));
    }

    @DataPermission(parameterName = "customerId",resolverName = "customerDataPermissionResolver",forceCheck = true)
    @RequestMapping("/contractInfo1")
    @ResponseBody
    public WebResult contractInfo1(QueryDto dto) {
        return WebResult.successWebResult(jdbcTemplate.queryForMap("select * from t_contract where customerId=?", dto.getCustomerId()));
    }

    @DataPermission(parameterName = "customerId",resolverName = "customerDataPermissionResolver")
    @RequestMapping("/contractInfo")
    @ResponseBody
    public WebResult contractInfo(String token, String customerId) {
        return WebResult.successWebResult(jdbcTemplate.queryForMap("select * from t_contract where customerId=?", customerId));
    }

    @RequestMapping("/contractModify")
    public String contractModify(String token, String id, String tile, String content, String signPerson, String identityNo, String linkmanPhone) {
        String sql = "update t_contract set tile=?,content=?,signPerson=?,identityNo=?,linkmanPhone=? where id=?";
        jdbcTemplate.update(sql, tile, content, signPerson, identityNo, linkmanPhone, id);
        return "index";
    }
}
