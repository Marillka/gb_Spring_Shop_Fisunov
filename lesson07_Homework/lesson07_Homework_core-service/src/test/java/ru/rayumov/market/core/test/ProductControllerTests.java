package ru.rayumov.market.core.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.rayumov.market.api.ProductDto;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllProductsTest() throws Exception {
        mvc
                .perform(
                        get("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[8].title", is("Huawei")));
    }

    @Test
    public void getProductById() throws Exception {
        mvc
                .perform(
                        get("/api/v1/products/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.title", is("Butter")));
    }

    @Test
    public void deleteById() throws Exception {
        mvc
                .perform(
                        delete("/api/v1/products/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto productDto = new ProductDto(null, "Cherry", BigDecimal.valueOf(34.00), "Food");
        mvc
                .perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(productDto))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        mvc
                .perform(
                        get("/api/v1/products/10")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.title", is("Cherry")));
    }


}
