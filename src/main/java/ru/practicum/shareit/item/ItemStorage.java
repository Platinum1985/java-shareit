package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ItemStorage {
    public List<Item> items = new ArrayList<>();
    private int id = 1;

    public Item saveItem(Item item) {
        item.setId(id);
        log.info("Присвоили Id для Item = {}  репозиторий", item);
        items.add(item);
        id++;
        return item;
    }

    public Item getItemById(int itemId) {
        //  log.info("Список items = {}",items.toString());
        Item i = items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item with ID " + itemId + " not found"));
        log.info("item in repository = {}", i);
        return i;
    }

    public List<Item> getItemsForOwner(int ownerId) {
        return items.stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
    }

    public List<Item> getAllItems() {
        return items;
    }
}
