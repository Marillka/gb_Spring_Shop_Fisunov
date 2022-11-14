package ru.rayumov.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.dtos.ProductDto;
import ru.rayumov.market.exceptions.ResourceNotFoundException;
import ru.rayumov.market.entities.Product;
import ru.rayumov.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteByID(Long id) {
        productRepository.deleteById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
//        product.setCategory(categoryService.findByTitle(productDto.getTitle()).get());
        product.setCategory(categoryService.findByTitle(productDto.getTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория с названием: " + productDto.getCategoryTitle() + " не найдена")));

        productRepository.save(product);
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
