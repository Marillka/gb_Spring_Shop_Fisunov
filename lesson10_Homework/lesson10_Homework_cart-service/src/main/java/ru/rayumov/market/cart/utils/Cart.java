package ru.rayumov.market.cart.utils;


import lombok.Data;
import ru.rayumov.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public void add(ProductDto p) {
        for (CartItem item : items) {
            if (item.getProductId().equals(p.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice(), p.getCategoryTitle());
        items.add(cartItem);
        recalculate();
    }

    public void mergeCart(Cart cart) {
        List<CartItem> items1 = cart.getItems();
        items.addAll(items1);
    }

    public void remove(Long productId) {
        if (items.removeIf(p -> p.getProductId().equals(productId))) {
            recalculate();
        }
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
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

}
