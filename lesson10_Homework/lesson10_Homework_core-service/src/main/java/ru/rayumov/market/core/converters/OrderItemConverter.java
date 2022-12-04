package ru.rayumov.market.core.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.OrderItemDto;
import ru.rayumov.market.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(
                o.getProduct().getId(),
                o.getProduct().getTitle(),
                o.getQuantity(),
                o.getPricePerProduct(),
                o.getPrice());
    }
}
