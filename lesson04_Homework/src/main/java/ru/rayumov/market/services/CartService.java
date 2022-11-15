package ru.rayumov.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.exceptions.ResourceNotFoundException;
import ru.rayumov.market.entities.Product;
import ru.rayumov.market.utils.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(Long productId) {
        Product p = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + productId + " не найден"));
        cart.add(p);
    }

    public void deleteCart() {
        cart.clear();
    }

    public void changeQuantity(String productTitle, Integer delta) {
        cart.changeQuantity(productTitle, delta);
    }
}
