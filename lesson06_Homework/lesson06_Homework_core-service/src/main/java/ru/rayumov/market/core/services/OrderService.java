package ru.rayumov.market.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.CartDto;
import ru.rayumov.market.api.CartItemDto;
import ru.rayumov.market.core.entities.Order;
import ru.rayumov.market.core.entities.OrderItem;
import ru.rayumov.market.core.entities.Product;
import ru.rayumov.market.core.exceptions.ResourceNotFoundException;
import ru.rayumov.market.core.integrations.CartServiceIntegration;
import ru.rayumov.market.core.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private final CartServiceIntegration cartServiceIntegration;

//    private final OrderConverter orderConverter;

//    public List<OrderDto> getAllOrders(String username) {
//        User user = userService.findByUsername(username).get();
//        List<Order> orders = orderRepository.findAllByUser(user);
//        return orders.stream()
//                .map(orderConverter::entityToDto)
//                .collect(Collectors.toList());
//    }

    // добавить проверку на пустую корзину.

    // orderConverter
    @Transactional
    public void createNewOrder(String username) {

            Order order = new Order();
            CartDto cart = cartServiceIntegration.getCurrentCart();

            order.setUsername(username);
            order.setTotalPrice(cart.getTotalPrice());


            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItemDto cartItem : cart.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPricePerProduct(cartItem.getPricePerProduct());

                Product product = productService.findByTitle(cartItem.getProductTitle()).orElseThrow(() -> new ResourceNotFoundException("Продукт с названием: " + cartItem.getProductTitle()));

                orderItem.setProduct(product);

                orderItems.add(orderItem);
            }

            order.setItems(orderItems);
            orderRepository.save(order);

//            cartServiceIntegration.clearCart();

    }
}
