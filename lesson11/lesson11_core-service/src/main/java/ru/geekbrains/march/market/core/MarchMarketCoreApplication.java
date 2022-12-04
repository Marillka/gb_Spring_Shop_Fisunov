package ru.geekbrains.march.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarchMarketCoreApplication {

	// План действий:
	// 1. Подключение платёжной системы / OAuth 2.0
	// 2. Безопасность на gateway
	// 3. Перевести WebClient в асинхронный режим
	// 4. Docker, WireMock, Swagger.

	public static void main(String[] args) {
		SpringApplication.run(MarchMarketCoreApplication.class, args);
	}
}
