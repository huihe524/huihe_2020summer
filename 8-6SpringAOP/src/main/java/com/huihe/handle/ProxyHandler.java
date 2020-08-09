package com.huihe.handle;

import com.huihe.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyHandler implements InvocationHandler {
    private Object target;
    private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

    public ProxyHandler(Object target){
        this.target = target;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{UserService.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        Object result = method.invoke(target, args);
        return result;
    }

    private void log(String method){
        System.out.println("[Debug] "+sdf.format(new Date())+"执行了方法"+method);
    }
}
