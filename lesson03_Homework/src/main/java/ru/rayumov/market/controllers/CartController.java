package ru.rayumov.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.converters.CartConverter;
import ru.rayumov.market.dtos.CartDto;
import ru.rayumov.market.services.CartService;

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

    @DeleteMapping("/delete/all")
    public void deleteCartEntire() {
        cartService.deleteCart();
    }

    @GetMapping("/change_quantity")
    public void changeQuantity(@RequestParam String productTitle, @RequestParam Integer delta) {
        cartService.changeQuantity(productTitle, delta);

    }


}