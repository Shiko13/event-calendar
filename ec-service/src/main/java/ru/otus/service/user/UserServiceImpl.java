package ru.otus.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.NewUserRequest;
import ru.otus.converter.UserConverter;
import ru.otus.dto.UserDto;
import ru.otus.model.User;
import ru.otus.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        log.info("Getting all users from repository");
        List<User> resultList;
        if (ids.isEmpty()) {
            resultList = userRepository.findAll(PageRequest.of(from / size, size)).toList();
        } else {
            resultList = userRepository.findAllById(ids);
        }
        return resultList.stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest request) {
        log.info("Creating user");
        User user = UserConverter.fromDto(request);
        User savedUser = userRepository.save(user);
        UserDto newDto = UserConverter.toDto(savedUser);
        log.info("New user with id {}, name {} & email {} created.",
                newDto.getId(),
                newDto.getName(),
                newDto.getEmail());

        return newDto;
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
        log.info("User with id {} deleted successfully", userId);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("This user not found");
        }
        return user.get();
    }
}
