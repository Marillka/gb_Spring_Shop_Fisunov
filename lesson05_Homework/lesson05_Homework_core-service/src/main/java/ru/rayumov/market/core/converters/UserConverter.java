package ru.rayumov.market.core.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.api.UserDto;
import ru.rayumov.market.core.entities.User;

@Component
public class UserConverter {

    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
