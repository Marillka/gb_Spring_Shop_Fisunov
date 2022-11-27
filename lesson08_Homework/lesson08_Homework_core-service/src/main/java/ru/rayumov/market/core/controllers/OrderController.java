package ru.rayumov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.api.OrderDetails;
import ru.rayumov.market.api.OrderDto;
import ru.rayumov.market.core.converters.OrderConverter;
import ru.rayumov.market.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findUserOrders(username).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username, @RequestBody OrderDetails orderDetails) {
        orderService.createNewOrder(username);
    }

}

