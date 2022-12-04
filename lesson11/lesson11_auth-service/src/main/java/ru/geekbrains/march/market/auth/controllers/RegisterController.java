package ru.geekbrains.march.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.march.market.api.RegisterUserDto;
import ru.geekbrains.march.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void registerNewUser(@RequestBody RegisterUserDto registerUserDto) {
        //TODO полностью реализовать метод, как считаете нужным.
        // ниже всего лишь пример хеширования паролей.
        String bcryptCachedPassword = passwordEncoder.encode(registerUserDto.getPassword());

    }


    // Регистрация пользователя
    // Проверить что пользователя не существует
    // Перед сохранением его в базу пароль преобразовать к хешу через BCrypt
}
