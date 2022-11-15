package com.rayumov.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/*
@EnableAspectJAutoProxy - Включаем AOP подход.

 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.rayumov.aop")
public class AppConfig {
}
