package ru.practicum.shareit.request;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ResponseEntity<ItemRequest> createItemRequest(@RequestBody ItemRequestDto itemRequestDto, @RequestHeader int requestorId) {
        ItemRequest itemRequest = itemRequestService.addItemRequest(itemRequestDto, requestorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRequest);
    }

    @GetMapping
    public List<ItemRequest> getAllItemRequests() {
        return itemRequestService.getAll();
    }
}
