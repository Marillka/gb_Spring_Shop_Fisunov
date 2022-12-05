package ru.rayumov.market.cart.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Корзины", description = "Методы работы с  корзинами")
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;


    @Operation(
            summary = "Запрос на генерацию гостевого Id для козины",
            responses = {
                    @ApiResponse(
                            description = "GuestCartId успешно создан", responseCode = "200"
                    )
            }
    )
    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Operation(
            summary = "Запрос на получение корзины",
            responses = {
                    @ApiResponse(
                            description = "Корзина получена", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username, @PathVariable @Parameter(description = "Id гостевой корзины", required = true) String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        return cartConverter.entityToDto(cartService.getCurrentCart(currentCartId));
    }

    @Operation(
            summary = "Запрос на добавление продукта в корзину",
            responses = {
                    @ApiResponse(
                            description = "Продукт добавлен в корзину", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username, @PathVariable @Parameter(description = "Id гостевой корзины", required = true) String guestCartId, @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @Operation(
            summary = "Запрос на очистку корзины",
            responses = {
                    @ApiResponse(
                            description = "Корзина очищена", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{guestCartId}/clear")
    public void clearCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username, @PathVariable @Parameter(description = "Id гостевой корзины", required = true) String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.clearCart(currentCartId);
    }

    @Operation(
            summary = "Запрос на изменение количества продуктов в корзине",
            responses = {
                    @ApiResponse(
                            description = "Количество изменено", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}/change_quantity")
    public void changeQuantity(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username, @PathVariable @Parameter(description = "Id гостевой корзины", required = true) String guestCartId, @RequestParam String productTitle, @RequestParam @Parameter(description = "+1 или -1", required = true) Integer delta) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.changeQuantity(currentCartId, productTitle, delta);
    }

    @Operation(
            summary = "Запрос слияние гостевой и пользовательской корзин",
            responses = {
                    @ApiResponse(
                            description = "Количество изменено", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCartId}/merge_carts")
    public void mergeCarts(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username, @PathVariable @Parameter(description = "Id гостевой корзины", required = true) String guestCartId) {
        cartService.mergeCarts(guestCartId, username);
    }

    private String selectCartId(String username, String questCartId) {
        if (username != null) {
            return username;
        }
        return questCartId;
    }


}