package com.example.web;

import com.example.web.base.WebResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RequestMapping("/base")
public class Application {
    //模仿redis存储登录用户token及用户信息
    public static Map<String, Map<String,Object>> map=new HashMap<>(128);

    @RequestMapping("/login")
    @ResponseBody
    public ModelAndView login(String username, String password){
        ModelAndView mv=new ModelAndView();
        //登录逻辑省略。。。
        Map<String,Object> dd=new HashMap<>();
        dd.put("userId",1);
        //本来应该用某些规则生成不重复的token字符串，这里不是重点，做个示意即可
        String token="hhhddddaaa123456";
        map.put(token,dd);
        mv.addObject("token",token);
        mv.setViewName("index");
        return mv;
    }
    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }
}
