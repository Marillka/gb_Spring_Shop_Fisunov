package ru.rayumov.market.core.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.core.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
