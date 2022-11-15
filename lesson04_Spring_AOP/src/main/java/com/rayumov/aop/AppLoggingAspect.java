package com.rayumov.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AppLoggingAspect {

    // "execution(modifier-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(args-pattern)
    // throws-pattern?)"
    // execution([модификатор_метода(public, *)?] [тим возврата] [класс?] [имя_метода] ([аргументы]) [исключения]
    // если стоит знак вопроста - то можно это не указывать

//    @Before("execution(public void com.rayumov.aop.UserDAO.addUser())") // pointcut expression
//    public void beforeAddUserInUserDAOClass() {
//        System.out.println("AOP: Поймали добавление пользователя");
//    }

//     в pointcut expression можно использовать wildcard - то есть можете говорить что до слова User может идти любая последовательность символов.
//     или вот так например *User*()
//    @Before("execution(public void com.rayumov.aop.UserDAO.*User())")
//    public void beforeUserModifyInUserDAOClass() {
//        System.out.println("AOP: работа с пользователем в UserDAO");
//    }

//    @Before("execution(public void com.rayumov.aop.UserDAO.*())")
//    public void beforeAnyMethodWithoutArgsInUserDAOClass() {
//        System.out.println("AOP: любой void метод без аргументов из UserDAO");
//    }

    // Что означают (..) в параметрах метода?
    // Если скобки пустые - метод без аргументов
    // (*) - у метода строго один аргумент
    // (..) - у метода больше одного аргумента.
//    @Before("execution(public void com.rayumov.aop.*.*(..))")
//    public void beforeAnyMethodInUserDAOClass() {
//        System.out.println("AOP: любой метод без аргументов из UserDAO");
//    }


    /*
        Если нужна какая то дополнительная информация о работе метода, тогда нужно заинжектить сюда JoinPoint (точка привязки к методу).
        И из этого JoinPoint например выдергивать сигнатуру метода. И можно запросить списко аргументов и отпечатать их.
     */
//    @Before("execution(public void com.rayumov.aop.UserDAO.*(..))")
//    public void beforeAnyMethodInUserDAOClassWithDetails(JoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        System.out.println("В UserDAO был вызван метод: " + methodSignature);
//        Object[] args = joinPoint.getArgs();
//        if (args.length > 0) {
//            System.out.println("Аргументы:");
//            for (Object o : args) {
//                System.out.println(o);
//            }
//        }
//    }


    /*
    Перехват события, возврат какого то результата.
    Перехватываем возврат из метода getAllUsers(). И то, что он вернет положем в 'result'.
    Перехватываем лист Юзеров и ставим на 0 позицию свои данные.
     */
    @AfterReturning(
            pointcut = "execution(public * com.rayumov.aop.UserDAO.getAllUsers(..))",
            returning = "result")
            public void afterGetBobInfo(JoinPoint joinPoint, List<String> result) {
                result.set(0, "Donald Duck");
    }

    // перехват броска исключений
    @AfterThrowing(
            pointcut = "execution(public * com.rayumov.aop.UserDAO.*(..))",
            throwing = "exc")
    public void afterThrowing(JoinPoint joinPoint, Throwable exc) {
        System.out.println(exc);
    }

    // после метода
    @After("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public void afterMethod() {
        System.out.println("After");
    }

    // Вокруг метода
    /*
    Допустим вы хотите засечь сколько времени исполняется метод.
    Добавляем ProceedingJoinPoint - это сслыка на исполняемый метод. Он как бы еще не запустился, но вы можете его запустить.
    proceed() - говорим Метод исполняйся. Рузальтат получаем и кладем в какой то объект.
    Возвращаем результат метода.
     */
    @Around("execution(public * com.rayumov.aop.UserDAO.*(..))")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("start profiling");
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        System.out.println((MethodSignature) proceedingJoinPoint.getSignature() + " duration: " + duration);
        System.out.println("end profiling");
        return out;
    }





}
