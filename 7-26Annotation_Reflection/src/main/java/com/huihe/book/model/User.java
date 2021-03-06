package com.huihe.book.model;

import com.huihe.annotation.MyAnnotation;

public class User {
    //Flied
    @MyAnnotation("hxj")
    private String username;

    @MyAnnotation("123")
    private String password;

    public User(){}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Method
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
