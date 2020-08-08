package com.huihe;


import com.huihe.annotation.MyAnnotation;
import com.huihe.model.User;
import org.junit.Test;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> c = Class.forName("com.huihe.model.User");
//        System.out.println(c.getSimpleName());
//        System.out.println(c.getName());
        Field field = c.getDeclaredField("username");
        User user = new User("1", "2");
//        System.out.println(user);
        field.setAccessible(true); //跳过安全检测 / 暴力反射
        field.set(user, "123");
//        System.out.println(user);
        c.getFields(); //拿到所有的公共权限的属性 / 包括从父类继承
        Field[] fields = c.getDeclaredFields();// 拿到当前类的所有属性 / 不包括继承的
        Method username = c.getMethod("getUsername");
        Object o = username.invoke(user);
//        System.out.println(o);
        c.getMethods(); //拿到所有的公共权限的方法 / 包括从父类继承
        c.getDeclaredMethods(); // 拿到当前类的所有方法 / 不包括继承的
        Constructor<?> constructor = c.getConstructor();
        Object instance = constructor.newInstance();
//        System.out.println(instance);
        Object instance1 = c.newInstance();

        System.out.println(instance1);
    }

    @Test
    public void test() throws ClassNotFoundException {
        Class<User> c1 = User.class;
        User user = new User();
        Class<? extends User> c2 = user.getClass();
        Class<?> c3 = Class.forName("com.huihe.model.User");
        Class<?> c4 = Class.forName("com.huihe.model.User");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c1 == c2 && c1 == c3 && c1 == c4);
    }


    @Test
    public void test1(){
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;
        Class c6 = Override.class;
        Class c7 = int.class;
        Class c8 = void.class;
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);
        System.out.println(c8);
    }

    @Test
    public void test3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> c = Class.forName("com.huihe.model.User");
        Field[] fields = c.getDeclaredFields();
        Object instance = c.newInstance();
        for(Field field : fields){
            MyAnnotation annotation = field.getAnnotation(MyAnnotation.class);
            field.setAccessible(true);
            System.out.println(annotation.value());
            field.set(instance, annotation.value());
        }
        System.out.println(instance);
    }
    @Test
    public void test4() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream("/bean.properties");
        properties.load(inputStream);
        String clz = properties.getProperty("class", "");
        Class<?> c = Class.forName(clz);
        Object instance = c.newInstance();
        for(Field field : c.getDeclaredFields()){
            field.setAccessible(true);
            field.set(instance, properties.getProperty(field.getName(),""));
        }
        System.out.println(instance);
    }


    @Test
    public void test5() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream("/bean.properties");
        properties.load(inputStream);
        String str = properties.getProperty("class", "");
        Class<?> c = Class.forName(str);
        Object instance = c.newInstance();
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true); //跳过安全检查 暴力反射
            field.set(instance, properties.getProperty(field.getName(),""));
        }
        System.out.println(instance);
    }
}
