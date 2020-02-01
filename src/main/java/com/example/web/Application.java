package com.example.web;

import com.example.web.base.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RequestMapping("/base")
public class Application {
    //模仿redis存储登录用户token及用户信息
    public static Map<String, Map<String,Object>> redisMap =new HashMap<>(128);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/login")
    @ResponseBody
    public WebResult login(String username, String password){
        //密码就不做加解密了
        List<Map<String, Object>> userMapList=jdbcTemplate.queryForList("select * from t_user where name=? and password=?",username,password);
        if (userMapList==null || userMapList.size()==0){
            return WebResult.errorWebResult("用户名或密码错误");
        }
        Map<String,Object> dd=new HashMap<>();
        dd.put("userId",userMapList.get(0).get("id"));
        String token="test-"+ UUID.randomUUID().toString();
        redisMap.put(token,dd);
        return WebResult.successWebResult(token);
    }
    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }
}
