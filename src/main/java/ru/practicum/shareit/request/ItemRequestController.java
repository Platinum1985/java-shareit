package ru.practicum.shareit.request;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequest createItemRequest(@RequestBody ItemRequestDto itemRequestDto, @RequestHeader int requestorId) {
        return itemRequestService.addItemRequest(itemRequestDto, requestorId);
    }

    @GetMapping
    public List<ItemRequest> getAllItemRequests() {
        return itemRequestService.getAll();
    }
}
