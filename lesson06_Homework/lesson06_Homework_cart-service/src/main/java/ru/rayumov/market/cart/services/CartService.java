package ru.rayumov.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.cart.integrations.ProductServiceIntegration;
import ru.rayumov.market.cart.utils.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;

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
        ProductDto p = productServiceIntegration.findById(productId);
        cart.add(p);
    }

    public void deleteCart() {
        cart.clear();
    }

    public void changeQuantity(String productTitle, Integer delta) {
        cart.changeQuantity(productTitle, delta);
    }
}
