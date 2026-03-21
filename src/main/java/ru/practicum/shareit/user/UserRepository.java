package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Здесь можно добавить дополнительные методы для работы с пользователями
    boolean existsByEmail(String email);
}
