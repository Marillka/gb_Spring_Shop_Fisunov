package ru.rayumov.market.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.rayumov.market.api.RegisterUserDto;
import ru.rayumov.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Регистация", description = "Методы регистрации")
public class RegisterController {

    private final UserService userService;


    @Operation(
            summary = "Запрос на регистрацию пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешная регистрация", responseCode = "200"
                    )

            }
    )
    @PostMapping("/register")
    public void registerNewUser(@RequestBody @Parameter(description = "RegisterUserDto", required = true) RegisterUserDto registerUserDto) {
        userService.registerNewUser(registerUserDto);
    }
}
