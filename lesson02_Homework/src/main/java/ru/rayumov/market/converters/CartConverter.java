package ru.rayumov.market.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.dtos.CartDto;
import ru.rayumov.market.dtos.CartItemDto;
import ru.rayumov.market.utils.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();

        List<CartItemDto> cartItemDto = cart.getItems().stream().map(i -> new CartItemDto(i.getProductTitle(), i.getQuantity(), i.getPricePerProduct(), i.getPrice())).collect(Collectors.toList());

        cartDto.setItems(cartItemDto);
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }

}
