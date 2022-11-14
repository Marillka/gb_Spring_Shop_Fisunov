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
        /*
        BidDecimal - это имутабельный объект.
        То есть когда вы вызываете у него метод add - сам он не меняется, он создает новый объект, который больше предыдущего на прибавленную сумму и возвращает ссылку.
        Если ссылку на объект не переписать, то ничего не произойдет.
        Работаем с ним как со строками и как с любыми имутабельными объектами.
         */
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
