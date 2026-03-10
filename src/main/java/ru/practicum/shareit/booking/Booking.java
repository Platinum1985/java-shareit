package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Component
@EqualsAndHashCode(of = {"id"})
public class Booking {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    Item item;
    User booker;
    Status status;
}