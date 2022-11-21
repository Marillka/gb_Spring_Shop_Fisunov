package ru.geekbrains.march.market.core.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/*
В контроллеры прилетают запросы от Servlet Dispatcher и они должны выполнить набор каких то действий.
@AutoConfigureMockMvc - чтобы тестировать контроллер, мы не хотим поднимать целое web приложение - это слишком долго. Можно эмулировать поведение веба, как будто запрос от куда то пришел, но он приходить ниоткуда не будет, а будет сразу попадать в контроллер.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
}
