package com.rayumov.aop.complex;


/*
Если у вас есть какой то комплексный аспект.
То есть допустим есть три таких Before перехватчика и один из них что то логирует, второй анализирует, третий что то отправляет в облако.
То возможно что они должны работать в определенном порядке. Логирование, аналитика и отправка в облако.
Но вы же не можете гарантировать что они запустятся в определенном порядке.
Если вам нужно что то упорядочить, то вы можете разбить их на несклько отдельных аспектов. В кажом из которых прописать нужное усливиеи задать им некий порядок.
@Order(200)
@Order(1000)
@Order(-100)
Чем ниже порядок у бина, тем раньше он будет срабатывать.
 */

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class ComplexAspect {

    @Before("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public void allMethodsCallsLogging() {
        System.out.println("В классе UserDAO вызывают метод");
    }

    @Before("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public void allMethodsCallsAnalytics() {
        System.out.println("В классе UserDAO вызывают метод (Аналитика)");
    }

    @Before("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public void allMethodCallsSEndInfoToCloud() {
        System.out.println("В классе UserDAO вызывают метод (Cloud)");
    }


}
