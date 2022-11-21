package ru.geekbrains.march.market.core.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/*
В случае тестирование Web приложений, все чуть сложенее чем с обычным тестированием.
У вас есть некий контекст, какое то количество бинов, web.
И соответственно нужно чтобы JUnit5 понимал в каком окружении он находится.
Вот SpringBootTest - это вы объясняете JUnit5 что ты сейчас будешь работать в окружении спринга (то есть используй контекс и все эти настройки).

 */
@SpringBootTest
class MarchMarketCoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
