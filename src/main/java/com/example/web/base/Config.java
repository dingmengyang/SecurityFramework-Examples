package com.example.web.base;

import com.example.web.Application;
import org.jason.datapermissioncheck.DataPermissionCheckInterceptor;
import org.jason.datapermissioncheck.DataPermissionException;
import org.jason.datapermissioncheck.DataPermissionResolver;
import org.jason.datapermissioncheck.container.InitializingDataPermissionResolverContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@ControllerAdvice
public class Config extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @ExceptionHandler(value = {DataPermissionException.class})
    @ResponseBody
    public WebResult exceptionHandler(DataPermissionException e){
        return WebResult.exceptionWebResult(Integer.parseInt(e.getCode()),e.getErrorMessage());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataPermissionCheckInterceptor(new InitializingDataPermissionResolverContainer(applicationContext)));
    }

    @Bean
    public DataPermissionResolver customerDataPermissionResolver(@Autowired JdbcTemplate jdbcTemplate){
        return new DataPermissionResolver() {
            @Override
            public boolean hasDataPermission(HttpServletRequest httpServletRequest, Object parameter) {
                if (parameter==null || StringUtils.isEmpty(parameter.toString())){
                    return true;
                }
                String customerId=parameter.toString();
//                String token=httpServletRequest.getAttribute("token").toString();
                String token="hhhddddaaa123456";
                Integer userId = (Integer) Application.map.get(token).get("userId");
                int count = jdbcTemplate.queryForObject("select count(*) from t_customer where id=? and user_id=?", Integer.class, customerId, userId);
                return count>0;
            }
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }


}
