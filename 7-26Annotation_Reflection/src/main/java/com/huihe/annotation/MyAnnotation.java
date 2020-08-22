package com.huihe.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
//@Inherited
public @interface MyAnnotation {
    String value() default "";
}
