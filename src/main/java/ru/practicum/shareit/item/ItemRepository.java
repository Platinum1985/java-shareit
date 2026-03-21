package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Здесь можно добавить дополнительные методы для работы с пользователями
    // boolean existsByEmail(String email);
    List<Item> findByOwner(User owner);
}

