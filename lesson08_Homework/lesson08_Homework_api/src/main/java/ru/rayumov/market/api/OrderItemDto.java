package ru.rayumov.market.api;

import java.math.BigDecimal;

public class OrderItemDto {

    private OrderDto order;

    private ProductDto product;

    private BigDecimal pricePerProduct;

    private BigDecimal price;

    private int quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(OrderDto order, ProductDto product, BigDecimal pricePerProduct, BigDecimal price, int quantity) {
        this.order = order;
        this.product = product;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
        this.quantity = quantity;
    }


    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}




