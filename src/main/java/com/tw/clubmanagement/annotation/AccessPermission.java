package com.tw.clubmanagement.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessPermission {
    String hasRole() default " ";
}
