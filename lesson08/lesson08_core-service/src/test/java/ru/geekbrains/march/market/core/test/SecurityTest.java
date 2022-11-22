package ru.geekbrains.march.market.core.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/*
Если бы был монолит с Principal, то можно тестировать работу с юзерами.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void securityAccessAllowedTest() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void securityAccessDeniedTest() throws Exception {
        mockMvc.perform(get("api/v1/orders"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    // когда метод будет выполнятся, то в контексте будет лежать пользователь с таким именем и списком ролей.
    @WithMockUser(username = "Bob", roles = "ADMIN")
    public void securityCheckUserTest() throws Exception {
        mockMvc.perform(get("/api/v1/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Проверка на то что мы можем получить всю цепочку получения токена и доступа к беку с этим токеном.
    @Test
    public void securityTokenTest() throws Exception {
        // формируем JSON с реальным пользователем в БД
        String jsonRequest = "{\n" +
                "\t\"username\": \"bob\", \n" +
                "\t\"password\": \"100\" \n" +
                "}";
        // формируем запрос на адрес и подшиваем JSON
        MvcResult result = mockMvc.perform(
                post("/auth")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();// сохраняем ответ

        // вытаскиваем токен из запроса
        String token = result.getResponse().getContentAsString();
        // обрезаем, оставляем чисто токен
        token = token.replace("{\"token\":\"", "").replace("\"}", "");

        // отрправляем запрос.
        // в заголовок пихаем токен.
        mockMvc.perform(get("/api/v1/orders")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


}
