package ru.rayumov.market.auth.controllers;

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
public class RegisterController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerNewUser(@RequestBody RegisterUserDto registerUserDto) {
        userService.registerNewUser(registerUserDto);
    }
}
