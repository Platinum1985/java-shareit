package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int ownerId) {
        itemDto.setOwner(ownerId);
        log.info("itemDto = {} in controller", itemDto);
        Item item = itemService.addItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable int itemId,
                                           @RequestBody ItemDto itemDto,
                                           @RequestHeader("X-Sharer-User-Id") int ownerId) {
        itemDto.setId(itemId);
        itemDto.setOwner(ownerId);
        Item patchededItem = itemService.patchItem(itemDto, ownerId);
        return ResponseEntity.ok(patchededItem);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable int itemId) {
        ItemDto itemDto = itemService.getItemById(itemId);
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItemsForOwner(@RequestHeader("X-Sharer-User-Id") int ownerId) {
        List<Item> items = itemService.getAllItemsForOwner(ownerId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam("text") String text) {
        log.info("in controller text = {}", text);
        List<Item> items = itemService.searchAvailableItems(text);
        return ResponseEntity.ok(items);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoFoundIdException(NotFoundException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(ValidationException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneralException(Exception e) {
        return Map.of("error", "Произошла внутренняя ошибка сервера.");
    }
}
