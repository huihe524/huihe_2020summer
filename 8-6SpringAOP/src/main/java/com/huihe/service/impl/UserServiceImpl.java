package com.huihe.service.impl;

import com.huihe.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void insert() {
        System.out.println("insert");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void select(int a) {
        System.out.println("select");
    }

}
