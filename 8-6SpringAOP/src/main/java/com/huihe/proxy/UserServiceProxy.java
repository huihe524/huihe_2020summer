package com.huihe.proxy;

import com.huihe.service.UserService;
import com.huihe.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServiceProxy implements UserService {
    private UserService userService;

    private SimpleDateFormat  sdf =new SimpleDateFormat("yyyy-MM-dd");

    public UserServiceProxy(UserService userService){
        this.userService = userService;
    }

    @Override
    public void insert() {
        log("insert");
        userService.insert();
    }

    @Override
    public void delete() {
        log("delete");
        userService.delete();
    }

    @Override
    public void update() {
        log("update");
        userService.update();
    }

    @Override
    public void select(int a) {
        log("select");
        userService.select(a);
    }

    private void log(String method){
        System.out.println("[Debug] "+sdf.format(new Date())+"执行了方法"+method);
    }
}
