package ru.rayumov.market.api;

public class OrderDetails {
    private String address;
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
