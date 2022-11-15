package com.rayumov.aop.complex;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
//@Order(200)
public class SimpleLoggingAspect {

    @Before("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public void allMethodsCallsLogging() {
        System.out.println("В классе UserDAO вызывают метод");
    }
}
