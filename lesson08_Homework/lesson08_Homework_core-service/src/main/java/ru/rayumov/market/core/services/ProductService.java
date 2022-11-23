package ru.rayumov.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.core.converters.ProductConverter;
import ru.rayumov.market.core.entities.Product;
import ru.rayumov.market.core.exceptions.ResourceNotFoundException;
import ru.rayumov.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final ProductConverter productConverter;

    public List<ProductDto> findAll() {
        return (productRepository.findAll()).stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public void deleteByID(Long id) {
        productRepository.deleteById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория с названием: " + productDto.getCategoryTitle() + " не найдена")));

        productRepository.save(product);
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByTitle(String productTitle) {
        return productRepository.findByTitle(productTitle);
    }
}
