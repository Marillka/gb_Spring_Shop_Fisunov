package ru.rayumov.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rayumov.market.api.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

//    private final RestTemplate restTemplate;
//
//    public ProductDto findById(Long id) {
//        return restTemplate.getForObject("http://localhost:8189/market-core/api/v1/products/" + id, ProductDto.class);
//    }

    private final WebClient productServiceWebClient;

    public ProductDto findById(Long id) {
        return productServiceWebClient.get()
                .uri("api/v1/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

}
