package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // уникальный идентификатор вещи;

    @Column(name = "name")
    private String name; //— краткое название;

    @Column(name = "description")
    private String description; //— развёрнутое описание;

    @Column(name = "available")
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private User owner; //— владелец вещи;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemRequestId")
    private ItemRequest request; //— если вещь была создана по запросу другого пол

    public Item(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Item() {
    }
}
