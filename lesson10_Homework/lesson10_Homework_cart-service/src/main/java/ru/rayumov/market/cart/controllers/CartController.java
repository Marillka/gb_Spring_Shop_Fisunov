package ru.rayumov.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.api.CartDto;
import ru.rayumov.market.api.StringResponse;
import ru.rayumov.market.cart.converters.CartConverter;
import ru.rayumov.market.cart.services.CartService;

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

    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        return cartConverter.entityToDto(cartService.getCurrentCart(currentCartId));
    }

    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @DeleteMapping("/{guestCartId}/clear")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.clearCart(currentCartId);
    }

    @GetMapping("/{guestCartId}/change_quantity")
    public void changeQuantity(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @RequestParam String productTitle, @RequestParam Integer delta) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.changeQuantity(currentCartId, productTitle, delta);
    }

    @GetMapping("/{guestCartId}/merge_carts")
    public void mergeCarts(@RequestHeader String username, @PathVariable String guestCartId) {
        cartService.mergeCarts(guestCartId, username);
    }

    private String selectCartId(String username, String questCartId) {
        if (username != null) {
            return username;
        }
        return questCartId;
    }


}