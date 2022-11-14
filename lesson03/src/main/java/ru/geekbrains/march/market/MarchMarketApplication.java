package ru.geekbrains.march.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarchMarketApplication {
	// Домашнее задание:
	// 1. На фронте сделать форму с дмумя полями имя_юзера/пароль и кнопкой выполнить
	// 2. Сделайте endpoint /get_my_email, который должен вернуть в ответ email текущего пользователя.


	public static void main(String[] args) {
		SpringApplication.run(MarchMarketApplication.class, args);
	}
}
