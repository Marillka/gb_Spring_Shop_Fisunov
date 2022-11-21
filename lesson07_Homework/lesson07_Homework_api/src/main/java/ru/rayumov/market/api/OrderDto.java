package ru.rayumov.market.api;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

    private BigDecimal totalPrice;

    private UserDto user;

    private List<OrderItemDto> items;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public OrderDto() {
    }

    public OrderDto(BigDecimal totalPrice, UserDto user, List<OrderItemDto> items) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.items = items;
    }
}
