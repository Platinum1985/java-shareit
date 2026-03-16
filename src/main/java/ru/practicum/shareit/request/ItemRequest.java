package ru.practicum.shareit.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"id"})
public class ItemRequest {
    private int id;
    private String description;
    private User requestor; // пользователь создавший запрос
    private LocalDateTime created; // дата и время создания запроса

    public ItemRequest(String description, User requestor) {
        this.description = description;
        this.requestor = requestor;
    }
}
