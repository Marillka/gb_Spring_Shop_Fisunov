package ru.geekbrains.march.market.cart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {


    // нужен для отправки запросов.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
