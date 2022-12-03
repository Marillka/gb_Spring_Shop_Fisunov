package ru.geekbrains.march.market.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.march.market.core.entities.Category;
import ru.geekbrains.march.market.core.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;

/*
// @DataJpaTest - ограничивает тестирование только слоем Jpa.
В чем отличие от SpringBootTest. Все ваши тесты проходят в рамках транзакций, которые будут выполнять rollback.
Такие тесты грузят в контекс только слой репозиториев.
 */
@DataJpaTest
@ActiveProfiles("test")// береб sql file из папки test
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    /*
    В DataJpaTest можно заинжектить TestEntityManager - по сути это hibernate сессия, с ее помощью можно закидывать данные в базу.
     */
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findAllCategoriesTest() {
        Category category = new Category();
        category.setTitle("Электоника");
        category.setProducts(Collections.emptyList());
        entityManager.persist(category);
        entityManager.flush();// при запуску теста закидывает в базу данных.

        List<Category> categoriesList = categoryRepository.findAll();

        Assertions.assertEquals(4, categoriesList.size());
        Assertions.assertEquals("Еда", categoriesList.get(0).getTitle());
    }

}
