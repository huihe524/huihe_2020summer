package com.huihe.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MyLog {

    @Pointcut("execution(* com.huihe.service.impl.*Impl.*(..))")
    public void point(){}

    @Before("point()")
    public void before(){
        System.out.println("before！");
    }

    @After("point()")
    public void after(){
        System.out.println("after！");
    }

    @AfterReturning("point()")
    public void afterReturning(){
        System.out.println("afterReturning！");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before around");
        joinPoint.proceed();
        System.out.println("After around");
    }

    @AfterThrowing("point()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
}
