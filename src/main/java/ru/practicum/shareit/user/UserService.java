package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.shareit.exceptions.ValidationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User createUser(UserDto userDto) {
        User user = UserDtoMapper.toUser(userDto, 0); // второй параметр UserId изначально 0, будет задаваться в UserStorage при добавлении в репозиторий
        if (!validateUser(user)) {
            throw new ValidationException("Некорректно заполнены поля");
        }
        log.info("user = {} in service", user);
        return userStorage.addUser(user);
    }

    public User updateUser(UserDto userDto, int userId) {
        User user = UserDtoMapper.toUser(userDto, userId);
        user.setId(userId);
        log.info("User = {} in service update id = {}", user, userId);
        return userStorage.updateUser(user);
    }

    public void deleteUser(int id) {
        userStorage.deleteUserById(id);
    }

    public User getUserById(int id) {
        return userStorage.findUserById(id);
    }

    public boolean validateUser(User user) {
        // Проверка электронной почты
        if (!StringUtils.hasText(user.getEmail()) || !user.getEmail().contains("@")) {
            log.error("Не заполнено email или заполнен некорректно");
            return false;
        }

        if (!StringUtils.hasText(user.getName())) {
            log.error("Не заполнено name или заполнен некорректно");
            return false;
        }

        return true;
    }
}