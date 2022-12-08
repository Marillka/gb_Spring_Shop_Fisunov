package ru.geekbrains.march.market.core.controllers;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.march.market.core.services.OrderService;
import ru.geekbrains.march.market.core.services.PayPalService;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalHttpClient payPalClient;
    private final OrderService orderService;
    private final PayPalService payPalService;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
        // формируется запрос на создание заказа
        OrdersCreateRequest request = new OrdersCreateRequest();
        // вернуть representation. Означает что мы пользователя заредиректим куда то и мы должны вернуть страницу или окно, которое вернутся.
        request.prefer("return=representation");
        // в тело запроса зашиваем информацию о заказе
        request.requestBody(payPalService.createOrderRequest(orderId));
        // отправляем запрос и сохраняем ответ
        com.paypal.http.HttpResponse<Order> response = payPalClient.execute(request);
        // возвращаем ответ пользователю
        return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
    }


    // Если заапрувили заказ, то попадаем в этот метод
    @PostMapping("/capture/{payPalId}")
    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
        // формируем запрос
        OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
        request.requestBody(new OrderRequest());

        // исполняем запрос, и сохраняем ответ
        com.paypal.http.HttpResponse<Order> response = payPalClient.execute(request);
        // получаем результат с каким то статусом. С этого момента платеж считается пройденным
        Order payPalOrder = response.result();
        // если статус у заказа Completed - тогда переводим платеж в такое то состояние
        if ("COMPLETED".equals(payPalOrder.status())) {
            long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
            // Optional<com.geekbrains.spring.web.core.entities.Order> orderOptional = orderService.findById(orderId);


            return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
        }
        // если иной статус
        return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
    }
}
