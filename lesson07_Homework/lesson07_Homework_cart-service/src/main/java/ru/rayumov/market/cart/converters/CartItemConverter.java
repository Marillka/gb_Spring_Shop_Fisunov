package ru.rayumov.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.CartItemDto;
import ru.rayumov.market.cart.utils.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem cartItem) {
        return new CartItemDto(cartItem.getProductTitle(), cartItem.getQuantity(), cartItem.getPricePerProduct(), cartItem.getPrice());
    }
}
