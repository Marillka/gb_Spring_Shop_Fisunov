package ru.geekbrains.march.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.geekbrains.march.market.api.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;

    // 0 - заглушка, т.к в любом случае будем использовать заголовок username
    // переделать на RequestParam, чтобы он не болтался в пути и был необязательным.
    public CartDto getCurrentUserCart(String username) {
        return cartServiceWebClient.get()
                .uri("api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart(String username) {
        cartServiceWebClient.get()
                .uri("api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
