package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderDetailsDto {
    @Schema(description = "Адресс доставки", required = true, example = "г.Москва, ул. Комсомольская, д.5, кв. 54")
    private String address;

    @Schema(description = "Телефон покупателя", required = true, example = "79169984856")
    private String phone;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
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
