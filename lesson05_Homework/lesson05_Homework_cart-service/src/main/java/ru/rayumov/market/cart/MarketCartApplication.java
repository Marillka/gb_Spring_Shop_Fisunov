package ru.rayumov.market.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketCartApplication {

    // Домашнее задание:
    // 1. Добавить к магазину возможность выгрузки всех товаров и отдельных товаров по id через SOAP

    public static void main(String[] args) {
        SpringApplication.run(MarketCartApplication.class, args);
    }
}
