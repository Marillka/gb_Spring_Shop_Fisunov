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
	// 5. Поговорить про профили
	// 6. Структура проектов (апи)

	// Домашнее задание
	// 1. Замените Page, который сейчас используется на тип PageDto (подумайте что в нем может быть).
	// 2. Исследовательская задача: Безопасность на gateway (либо реализация, либо предложения).
	// Например пускать в карт-сервис только авторизованных пользователей.
	public static void main(String[] args) {
		SpringApplication.run(MarchMarketCoreApplication.class, args);
	}
}
