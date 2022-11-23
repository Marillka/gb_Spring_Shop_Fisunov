package ru.rayumov.market.auth.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.UserDto;
import ru.rayumov.market.auth.entities.User;

@Component
public class UserConverter {

    public UserDto entityToDto(User user) {
        return new UserDto(user.getUsername(), user.getEmail());
    }
}
