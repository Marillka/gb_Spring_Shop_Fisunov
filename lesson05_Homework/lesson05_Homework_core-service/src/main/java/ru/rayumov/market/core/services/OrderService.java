package ru.rayumov.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.CartDto;
import ru.rayumov.market.api.CartItemDto;
import ru.rayumov.market.core.integrations.CartServiceIntegration;
import ru.rayumov.market.core.repositories.OrderItemRepository;
import ru.rayumov.market.core.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartServiceIntegration cartServiceIntegration;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;


    public void createNewOrder(String name) {
        CartDto currentCart = cartServiceIntegration.getCurrentCart();
        List<CartItemDto> items = currentCart.getItems();

        CartItemDto cartItemDto = items.get(0);




    }
}
