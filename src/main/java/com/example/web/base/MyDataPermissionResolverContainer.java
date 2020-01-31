package com.example.web.base;

import org.jason.datapermissioncheck.DataPermission;
import org.jason.datapermissioncheck.DataPermissionResolver;
import org.jason.datapermissioncheck.container.DataPermissionResolverContainer;
import org.jason.datapermissioncheck.container.InitializingDataPermissionResolverContainer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class MyDataPermissionResolverContainer implements DataPermissionResolverContainer, ApplicationContextAware {

    private DataPermissionResolverContainer delegate;

    @Override
    public void addResolver(String s, DataPermissionResolver dataPermissionResolver) {
        delegate.addResolver(s,dataPermissionResolver);
    }

    @Override
    public void removeResolver(String s) {
        delegate.removeResolver(s);
    }

    @Override
    public DataPermissionResolver getResolver(Method method, Class<?> aClass) {
        return delegate.getResolver(method,aClass);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public DataPermission getDataPermission(Method method, Class<?> aClass) {
        return delegate.getDataPermission(method,aClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.delegate=new InitializingDataPermissionResolverContainer(applicationContext);
    }
}
