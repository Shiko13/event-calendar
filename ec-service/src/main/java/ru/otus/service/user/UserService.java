package ru.otus.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.model.NewUserRequest;
import ru.otus.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    UserDto create(NewUserRequest request);

    void deleteById(Long userId);
}
