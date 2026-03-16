package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.DataBaseException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserStorage {
    private final List<User> users = new ArrayList<>();
    private int id = 1;

    public User addUser(User user) {
        if (checkDuplicateEmail(user.getEmail())) {
            throw new DataBaseException("Такой email уже используется");
        }
        user.setId(id);
        log.info("Присвоили пользователю userId = {}", id);
        users.add(user);
        log.info("Добавили пользователя {} в репозиторий", user);
        if (users.contains(user)) {
            log.info("Пользователь успешно добавлен: {}", user);
            id++;
            log.info("id следующего польз = {}", id);
            return user;
        } else {
            throw new DataBaseException("Ошибка при добавлении пользователя");
        }
    }

    public User updateUser(User user) {
        Optional<User> optionalUser = users.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst();
        log.info("Optional = {}", optionalUser);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (user.getName() != null && !user.getName().isEmpty()) {
                existingUser.setName(user.getName());
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                if (users.stream().filter(u -> u.getEmail().equals(user.getEmail())).count() > 0) {
                    log.info("existingEmail = {} change email = {}", existingUser.getEmail(), user.getEmail());
                    throw new DataBaseException("Обновляемый email не отличается от старого");
                }
                existingUser.setEmail(user.getEmail());
            }
        } else {
            log.warn("Пользователь с ID {} не найден", user.getId());
            throw new NotFoundException("Пользователь с таким ID не найден");
        }
        return user;
    }

    public void deleteUserById(int id) {
        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь с ID {} не найден"));

        users.remove(user);
        log.info("Пользователь с ID {} успешно удалён", id);
    }

    public User findUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
    }

    public boolean checkDuplicateEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }
}
