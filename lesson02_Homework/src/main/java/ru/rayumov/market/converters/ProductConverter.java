package ru.rayumov.market.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.dtos.ProductDto;
import ru.rayumov.market.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryTitle(product.getCategory().getTitle());
        return productDto;
    }
}
