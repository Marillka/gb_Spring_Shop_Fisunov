package com.rayumov.aop;


import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
//@Component
public class PointcutDeclarationAspect {

    /*
    Вот такие выражения можно сохранять в как бы переменные @Pointcut.
    Из них можно собрать другой @Pointcut.
    И их можно засовывать в @Before... и т.д.
     */

    @Pointcut("execution(public * com.rayumov.aop.UserDAO.get*(..))")
    public void userDAOGetTrackerPointcut() {

    }

    @Pointcut("execution(public * com.rayumov.aop.UserDAO.set*(..))")
    public void userDAOSetTrackerPointcut() {

    }

    @Pointcut("userDAOGetTrackerPointcut() || userDAOSetTrackerPointcut()")
    public void userDAOGetOrSetTrackerPointcut() {

    }

    @Before("userDAOGetOrSetTrackerPointcut()") // || !&&
//    @Before("execution(public * com.rayumov.aop.UserDAO.get*(..)) || execution(public * com.rayumov.aop.UserDAO.set*(..))")
    public void userDAOGetOrSetTracker() {
        System.out.println("В классе UserDAO вызывают геттер или сеттер");

    }
}
