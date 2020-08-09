package com.huihe;

import com.huihe.handle.ProxyHandler;
import com.huihe.proxy.UserServiceProxy;
import com.huihe.service.UserService;
import com.huihe.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        UserServiceProxy proxy = new UserServiceProxy(service);
        proxy.delete();
    }

    @Test
    public void test(){
        UserService service = new UserServiceImpl();
        ProxyHandler handler = new ProxyHandler(service);
        UserService proxy = (UserService)handler.getProxy();
        proxy.delete();
    }

    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService service = context.getBean("userService", UserService.class);
        service.select(1);
    }
}
