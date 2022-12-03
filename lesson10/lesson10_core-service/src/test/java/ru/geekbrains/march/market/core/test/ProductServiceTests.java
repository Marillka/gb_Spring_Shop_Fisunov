package ru.geekbrains.march.market.core.test;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.entities.Category;
import ru.geekbrains.march.market.core.repositories.ProductRepository;
import ru.geekbrains.march.market.core.services.CategoryService;
import ru.geekbrains.march.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
// даем доступ к контексту спринга
@SpringBootTest
//@SpringBootTest(classes = ProductService.class) // в контекс загрузится только продуктовый сервис
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    /*
    ProductService для работы использует productRepository и categoryService.
    Но если их сюда заинжектить, то получится что мы тестируем не один productService, а три.
    Поэтому из них нужно сделать моки - заглушки (подделки).
     */

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void createNewProductTest() {
        // Задаем заглушке поведение
        // Поднимется контекс и туда упадет MockBean categoryService, с тем поведением, который мы задали.
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Еда");
        category.setProducts(Collections.emptyList());
        Mockito.doReturn(Optional.of(category))
                .when(categoryService)
                .findByTitle("Еда");

        ProductDto productDto = new ProductDto(null, "Апельсины", BigDecimal.valueOf(100.0), "Еда");
        productService.createNewProduct(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }



}
