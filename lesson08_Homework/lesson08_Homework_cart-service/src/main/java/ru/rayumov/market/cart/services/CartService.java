package ru.rayumov.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.ProductDto;
import ru.rayumov.market.cart.integrations.ProductServiceIntegration;
import ru.rayumov.market.cart.utils.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;

    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String cartId) {
        if (!carts.containsKey(cartId)) {
            Cart cart = new Cart();
            carts.put(cartId, cart);
        }
        return carts.get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        ProductDto p = productServiceIntegration.findById(productId);
        getCurrentCart(cartId).add(p);
    }

    public void clearCart(String cartId) {
        getCurrentCart(cartId).clear();
    }

    public void changeQuantity(String cartId, String productTitle, Integer delta) {
        getCurrentCart(cartId).changeQuantity(productTitle, delta);
    }
}
