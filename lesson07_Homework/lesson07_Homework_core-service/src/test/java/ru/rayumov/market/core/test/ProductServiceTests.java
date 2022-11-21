package ru.rayumov.market.core.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.core.entities.Category;
import ru.rayumov.market.core.repositories.ProductRepository;
import ru.rayumov.market.core.services.CategoryService;
import ru.rayumov.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryService categoryService;

    @Test
    public void createNewProductTest() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Food");
        category.setProducts(Collections.emptyList());
        Mockito.doReturn(Optional.of(category))
                .when(categoryService)
                .findByTitle("Food");

        ProductDto productDto = new ProductDto(null, "Banana", BigDecimal.valueOf(100.00), "Food");
        productService.createNewProduct(productDto);


    }
}
