package ru.rayumov.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.api.CartDto;
import ru.rayumov.market.cart.converters.CartConverter;
import ru.rayumov.market.cart.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;


    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId) {
        cartService.addToCart(productId);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }

    @GetMapping("/change_quantity")
    public void changeQuantity(@RequestParam String productTitle, @RequestParam Integer delta) {
        cartService.changeQuantity(productTitle, delta);

    }


}