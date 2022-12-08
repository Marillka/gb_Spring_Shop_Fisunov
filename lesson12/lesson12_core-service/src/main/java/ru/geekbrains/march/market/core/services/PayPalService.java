package ru.geekbrains.march.market.core.services;

import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final OrderService orderService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
        // Достаем заказ из базы
        Order order = orderService.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        // формируем запрос
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Spring Web Market")// название магазина
                .landingPage("BILLING")// страница, которую нужно показать
                .shippingPreference("SET_PROVIDED_ADDRESS");// нужно предоставить адресс на доставку
        orderRequest.applicationContext(applicationContext);// подшиваем контекст в заказ

        // формируем список покупок
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        // формируем единицу покупки
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                // указываем orderId
                .referenceId(orderId.toString())
                // название заказа
                .description("Spring Web Market Order")
                // указываем валюту и общую стоимость заказа
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice())))))
                // берем наши items и преобразуем в те, которые понимает paypal
                .items(order.getItems().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProduct().getTitle())
                                .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity())))
                        .collect(Collectors.toList()))
                // заполняем адресс доставки
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername()))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        // кладем заказ в запрос
        purchaseUnitRequests.add(purchaseUnitRequest);
        // собираем запрос
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
