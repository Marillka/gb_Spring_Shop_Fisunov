package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Лист с OrderItemDto", required = true)
    private List<OrderItemDto> items;

    @Schema(description = "Стоимость заказа", required = true, example = "4323.00")
    private BigDecimal totalPrice;

    @Schema(description = "Адресс доставки", required = true, example = "г. Москва, ул. Комсомольская, д. 5, кв. 43")
    private String address;

    @Schema(description = "Номер телефона", required = true, example = "79955030087")
    private String phone;

    public OrderDto() {
    }


    public OrderDto(Long id, List<OrderItemDto> items, BigDecimal totalPrice, String address, String phone) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
