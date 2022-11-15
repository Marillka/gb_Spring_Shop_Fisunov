package com.rayumov.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserDAO userDAO = context.getBean(UserDAO.class);

        System.out.println(userDAO.getAllUsers());

        /*
        System.out.println(userDAO.getClass().getName());
        com.rayumov.aop.UserDAO$$EnhancerBySpringCGLIB$$252a1b6c
        Отпечатали название класса.
        А это не наш класс, а класс созданный библиотекой CGLIB.
        Это библиотека для автогенерации кода, для создания proxy версий классов.
        То есть это не настоящий UserDAO. Его кто то модифицировал и поэтому он может вести себя как угодно.
        Если отключить @Aspect - то AOP работать не будет. Будет обычный UserDAO класс.
         */


        context.close();
    }
}
