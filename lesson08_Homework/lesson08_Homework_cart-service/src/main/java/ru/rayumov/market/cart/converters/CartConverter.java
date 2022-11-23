package ru.rayumov.market.cart.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rayumov.market.api.CartDto;
import ru.rayumov.market.cart.utils.Cart;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        return new CartDto(cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()), cart.getTotalPrice());
    }

}
