package ru.rayumov.market.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public void incrementQuantity() {
        if (quantity < 0) {
            quantity = 0;
        }
        quantity++;
        price = price.add(pricePerProduct);
        }


    public void decrementQuantity() {
        if (quantity < 0) {
            quantity = 0;
        }
        quantity--;
        price = price.subtract(pricePerProduct);
    }
}
