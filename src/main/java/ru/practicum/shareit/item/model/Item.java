package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@Data
@EqualsAndHashCode(of = {"id"})
public class Item {
    private int id; // уникальный идентификатор вещи;
    private String name; //— краткое название;
    private String description; //— развёрнутое описание;
    Boolean available; // — статус о том, доступна или нет вещь для аренды;
    private User owner; //— владелец вещи;
    private ItemRequest request; //— если вещь была создана по запросу другого пол

    public Item(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
