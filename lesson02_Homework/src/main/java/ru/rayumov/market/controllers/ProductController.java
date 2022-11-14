package ru.rayumov.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.converters.ProductConverter;
import ru.rayumov.market.dtos.ProductDto;
import ru.rayumov.market.exceptions.ResourceNotFoundException;
import ru.rayumov.market.entities.Product;
import ru.rayumov.market.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.findAll();
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