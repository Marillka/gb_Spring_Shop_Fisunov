package ru.rayumov.market.utils;


import lombok.Data;
import ru.rayumov.market.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public void add(Product p) {
        for (CartItem item : items) {
            if (item.getProductId().equals(p.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(cartItem);
        recalculate();
    }

    public void changeQuantity(String productTitle, Integer delta) {
        for (CartItem item : items) {
            if (item.getProductTitle().equals(productTitle)) {
                if (delta > 0) {
                    item.incrementQuantity();
                    recalculate();
                    if (item.getQuantity() <= 0) {
                        items.remove(item);
                    }
                    return;
                }

                if (delta < 0) {
                    item.decrementQuantity();
                    recalculate();
                    if (item.getQuantity() <= 0) {
                        items.remove(item);
                    }
                    return;
                }
            }
        }
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        // проходимся по всем CartItem и к общей стоимостьи прибавляем стоимость каждой позиции.
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

}
