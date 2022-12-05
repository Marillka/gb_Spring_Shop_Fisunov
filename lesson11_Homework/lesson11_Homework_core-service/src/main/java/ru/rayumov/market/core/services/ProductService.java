package ru.rayumov.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.core.converters.ProductConverter;
import ru.rayumov.market.core.entities.Product;
import ru.rayumov.market.core.exceptions.ResourceNotFoundException;
import ru.rayumov.market.core.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final ProductConverter productConverter;

    public Page<ProductDto> findAll(int page, int pageSize, Specification<Product> specification) {
        return productRepository.findAll(specification, PageRequest.of(page, pageSize))
                .map(productConverter::entityToDto);
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

}
