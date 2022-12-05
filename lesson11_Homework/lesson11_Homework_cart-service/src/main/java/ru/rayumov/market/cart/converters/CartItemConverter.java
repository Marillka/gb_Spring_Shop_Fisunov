package ru.rayumov.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.CartItemDto;
import ru.rayumov.market.cart.utils.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
