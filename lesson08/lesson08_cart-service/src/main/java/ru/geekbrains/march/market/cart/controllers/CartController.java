package ru.geekbrains.march.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.StringResponse;
import ru.geekbrains.march.market.cart.converters.CartConverter;
import ru.geekbrains.march.market.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    // может прийти username и id корзины.
    // если пользователь залогинился, то берем идентификатором корзины его имя
    // если нет - id корзины.
    @GetMapping("/{questCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String questCartId) {
        String currentCartId = selectCartId(username, questCartId);
        return cartConverter.entityToDto((cartService.getCurrentCart(currentCartId)));
    }

    @GetMapping("/{questCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String questCartId, @PathVariable Long productId) {
        String currentCartId = selectCartId(username, questCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @GetMapping("/{questCartId}/clear")
    public void clearCurrentCart(@RequestHeader(required = false) String username, @PathVariable String questCartId) {
        String currentCartId = selectCartId(username, questCartId);
        cartService.clearCart(currentCartId);
    }

    private String selectCartId(String username, String questCartId) {
        if (username != null) {
            return username;
        }
        return questCartId;
    }
}
