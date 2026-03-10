package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;
    private final ItemRequestService itemRequestService;

    public Item addItem(ItemDto itemDto) {
        if (!validationItemDto(itemDto)) {
            throw new ValidationException("Некорректно заполнены поля itemDto");
        }
        Item item = ItemDtoMapper.toItem(itemDto);
        log.info("item = {} сервис перед IF", item);
        if (itemDto.getRequest() != 0) {
            item.setRequest(itemRequestService.getItemRequestById(itemDto.getRequest()));
            log.info("строка 29 сервис");
        }
        User owner = userService.getUserById(itemDto.getOwner());
        item.setOwner(owner);
        log.info("item = {} сервис после IF", item);
        return itemStorage.saveItem(item);
    }

    public Item patchItem(ItemDto itemDto, int ownerId) {
        Item item = itemStorage.getItemById(itemDto.getId());
        if (item.getOwner().getId() == ownerId) {
            item.setName(itemDto.getName());
            item.setDescription(itemDto.getDescription());
            item.setAvailable(itemDto.getAvailable());
            return item;
        } else {
            throw new NotFoundException("You are not the owner of this item");
        }
    }

    public ItemDto getItemById(int itemId) {
        ItemDto itemDto = ItemDtoMapper.toItemDto(itemStorage.getItemById(itemId));
        log.info("itemDto in service = {}", itemDto);
        return itemDto;
    }

    public List<Item> getAllItemsForOwner(int ownerId) {
        return itemStorage.getItemsForOwner(ownerId);
    }

    public List<Item> searchAvailableItems(String searchText) {
        log.info("Поиск по тексту: {}", searchText);
        if (searchText.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAllItems().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable().booleanValue()
                        && (item.getName() != null && item.getName().toLowerCase().contains(searchText.toLowerCase())
                        || item.getDescription() != null && item.getDescription().toLowerCase().contains(searchText.toLowerCase())))
                .collect(Collectors.toList());
    }

    private boolean validationItemDto(ItemDto itemDto) {
        if (itemDto.getAvailable() == null) {
            return false;
        }
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            return false;
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            return false;
        }
        return true;
    }
}
