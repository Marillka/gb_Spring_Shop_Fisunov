package ru.rayumov.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rayumov.market.api.OrderDetailsDto;
import ru.rayumov.market.api.OrderDto;
import ru.rayumov.market.core.converters.OrderConverter;
import ru.rayumov.market.core.exceptions.AppError;
import ru.rayumov.market.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;


    @Operation(
            summary = "Запрос на получение заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        return orderService.findUserOrders(username).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Заказ успешно создан", responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Пустая корзина", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )

            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createNewOrder(username, orderDetailsDto);
    }

}

