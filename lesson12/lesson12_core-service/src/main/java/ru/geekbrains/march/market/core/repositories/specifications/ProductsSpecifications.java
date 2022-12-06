package ru.geekbrains.march.market.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.march.market.core.entities.Product;

import java.math.BigDecimal;

public class ProductsSpecifications {

    /*
    root - ссылка на объект с которым будем работать
    criteriaBuilder - позволяет использовать стандартные JPQL операторы (больше, меньше, like, not null...).
     */
    public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

}
