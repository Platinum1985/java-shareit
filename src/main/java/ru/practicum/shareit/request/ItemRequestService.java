package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DataBaseException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    public ItemRequest addItemRequest(ItemRequestDto itemRequestDto, int requestor) {
        User reqUser = userRepository.findById(requestor).orElseThrow(() -> new DataBaseException("User-Requestor не найден"));
        return itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto, reqUser));
    }

    public List<ItemRequest> getAll() {
        return itemRequestRepository.findAll();
    }

    public ItemRequest getItemRequestById(int id) {
        return itemRequestRepository.findById(id).orElseThrow();
    }
}
