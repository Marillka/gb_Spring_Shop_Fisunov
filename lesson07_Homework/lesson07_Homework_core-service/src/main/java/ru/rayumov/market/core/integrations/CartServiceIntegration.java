package ru.rayumov.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rayumov.market.api.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

//    private final RestTemplate restTemplate;

    private final WebClient cartServiceWebClient;


    public CartDto getCurrentCart() {
        return cartServiceWebClient.get()
                .uri("api/v1/cart")
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

//    public CartDto getCurrentCart() {
//        return restTemplate.getForObject("http://localhost:8190/market-cart/api/v1/cart", CartDto.class);
//    }

//    public void clearCart() {
//        restTemplate.getForObject("http://localhost:8190/market-cart/api/v1/cart/delete/all", ResponseEntity.class);
//    }
}



