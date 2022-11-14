package ru.rayumov.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.dtos.AddToCartDto;
import ru.rayumov.market.entities.Product;
import ru.rayumov.market.services.CartService;
import ru.rayumov.market.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProductsInCart() {
        return cartService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProductInCartById(@PathVariable Long id) {
        cartService.deleteByID(id);
    }

    @PostMapping()
    public void addProductInCart(@RequestBody Product product) {
        Optional<Product> optionalProduct = productService.findById(product.getId());
        Product newProduct = optionalProduct.get();
        AddToCartDto addToCartDto = new AddToCartDto(newProduct.getTitle(), newProduct.getPrice());
        cartService.addToCart(addToCartDto);
    }



}
