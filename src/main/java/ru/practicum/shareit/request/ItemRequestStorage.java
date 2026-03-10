package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository //Singleton -данная аннотация создает только 1 экземпляр класса
public class ItemRequestStorage {
    private final List<ItemRequest> itemRequests = new ArrayList<>();
    private int id = 1;

    public ItemRequest addItemRequest(ItemRequest itemRequest) {
        itemRequest.setId(id);
        LocalDateTime localeDateTime = LocalDateTime.now();
        itemRequest.setCreated(localeDateTime);
        itemRequests.add(itemRequest);
        id++;
        return itemRequest;
    }

    public List<ItemRequest> getAll() {
        return itemRequests;
    }

    public ItemRequest getItemRequestById(int id) {
        Optional<ItemRequest> itemRequestOpt = itemRequests.stream().filter(i -> i.getId() == id).findFirst();
        if (itemRequestOpt.isEmpty()) {
            throw new NotFoundException("Запрос с таким id не найден");
        } else {
            return itemRequestOpt.get();
        }
    }
}

