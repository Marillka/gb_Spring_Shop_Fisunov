package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderDetails {
    @Schema(description = "Адресс доставки", required = true, example = "г.Москва, ул. Комсомольская, д.5, кв. 54")
    private String address;

    @Schema(description = "Телефон покупателя", required = true, example = "79169984856")
    private String telephone;

    public OrderDetails() {
    }

    public OrderDetails(String address, String telephone) {
        this.address = address;
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
