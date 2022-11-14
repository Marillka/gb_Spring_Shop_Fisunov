package ru.rayumov.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarchMarketApplication {

    // Домашнее задание:
    // Создайте бин корзина с листом продуктов
    // Добавьте на фронте к каждому продукту кнопку добавить в корзину
    // Кнопка добавить в корзину должна складывать в бин корзина указанный продукт
    // Продукт может быть добавлен несколько раз, группировать продукты пока не надо.
    // Под таблицей с продуктами отрисуйте таблицу с корзиной
    public static void main(String[] args) {
        SpringApplication.run(MarchMarketApplication.class, args);
    }
}
