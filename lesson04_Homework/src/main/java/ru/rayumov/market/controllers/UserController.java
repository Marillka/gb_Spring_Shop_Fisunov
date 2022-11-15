package ru.rayumov.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rayumov.market.dtos.UserDto;
import ru.rayumov.market.services.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user_profile")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getUserProfile(Principal principal) {
        return userService.loadUserProfile(principal.getName());
    }
}
