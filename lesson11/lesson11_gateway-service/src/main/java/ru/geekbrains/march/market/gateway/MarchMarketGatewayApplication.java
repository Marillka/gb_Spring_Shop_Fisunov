package ru.geekbrains.march.market.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Документация по Gateway
// https://cloud.sping.io/spring-cloud.gateway/reference/html/

/*
Spring-gateway релизован не на spring, а на netty.
Чтобы фильтовать запросы, как то их перенаправлять сервлеты не нужны. Логика спринга не нужна. Всякие там сложные контексты тоже не нужны.
Реализованно просто как обычный http сервер.
Очень сильно ускоряет работы gateway.
 */
@SpringBootApplication
public class MarchMarketGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarchMarketGatewayApplication.class);
    }

}
