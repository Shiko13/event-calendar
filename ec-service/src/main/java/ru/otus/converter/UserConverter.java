package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.model.NewUserRequest;
import ru.otus.dto.UserDto;
import ru.otus.dto.UserShortDto;
import ru.otus.model.User;

@UtilityClass
public class UserConverter {

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User fromDto(NewUserRequest newUserRequest) {
        return new User(null, newUserRequest.getName(), newUserRequest.getEmail());
    }

    public static UserShortDto toShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
