package ru.rayumov.market.converters;

import org.springframework.stereotype.Component;
import ru.rayumov.market.dtos.UserDto;
import ru.rayumov.market.entities.User;

@Component
public class UserConverter {

    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
