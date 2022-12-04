package ru.rayumov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.core.converters.ProductConverter;
import ru.rayumov.market.core.entities.Product;
import ru.rayumov.market.core.exceptions.ResourceNotFoundException;
import ru.rayumov.market.core.repositories.specifications.ProductSpecifications;
import ru.rayumov.market.core.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> getAllProduct(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "page_size", defaultValue = "5") Integer pageSize,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<Product> spec = Specification.where(null);
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.titleLike(titlePart));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(BigDecimal.valueOf(minPrice)));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqualsThan(BigDecimal.valueOf(maxPrice)));
        }

        return productService.findAll(page - 1, pageSize, spec);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не найден")));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creteNewProduct(@RequestBody ProductDto productDto) {
        productService.createNewProduct(productDto);
    }
}