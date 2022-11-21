package ru.geekbrains.march.market.core.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.march.market.api.ProductDto;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

/*
В контроллеры прилетают запросы от Servlet Dispatcher и они должны выполнить набор каких то действий.
@AutoConfigureMockMvc - чтобы тестировать контроллер, мы не хотим поднимать целое web приложение - это слишком долго. Можно эмулировать поведение веба, как будто запрос от куда то пришел, но он приходить ниоткуда не будет, а будет сразу попадать в контроллер.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    // здесь не запускается tomcat, либо какой то внутренний контейнер сервлетов.
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllProductsTest() throws Exception {
        /*
        Обращаемся к нашему окружению и просим его выполнить какой то запрос.
         */
        mvc
                .perform(// perform - выполнение запроса
                        get("/api/v1/products")// get запрос относительно корня приложения
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())// печатаем что отправили в запросе и что получили в ответе.
                .andExpect(status().isOk())// ожидаем статус 200
                .andExpect(jsonPath("$").isArray())// ожидаем что пришедшиый JSON это массив
                .andExpect(jsonPath("$", hasSize(4)))// имеет размер 1
                .andExpect(jsonPath("$[3].title", is("Масло")));
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto productDto = new ProductDto(null, "demo", BigDecimal.valueOf(100.00), "Еда");
        mvc
                .perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(productDto))
//                                .header("username", "Bob")// Здесь не особо нужно

                )
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
