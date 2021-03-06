package com.huihe.book.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class User {

    @Value("hxj")
    private String username;

    @Resource
    private Dog dog;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", dog=" + dog +
                '}';
    }
}
