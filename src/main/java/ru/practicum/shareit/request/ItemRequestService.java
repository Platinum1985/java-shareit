package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestStorage itemRequestStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemRequest addItemRequest(ItemRequestDto itemRequestDto, int requestor) {
        User reqUser = userStorage.findUserById(requestor);
        return itemRequestStorage.addItemRequest(ItemRequestMapper.toItemRequest(itemRequestDto, reqUser));
    }

    public List<ItemRequest> getAll() {
        return itemRequestStorage.getAll();
    }

    public ItemRequest getItemRequestById(int id) {
        return itemRequestStorage.getItemRequestById(id);
    }
}
