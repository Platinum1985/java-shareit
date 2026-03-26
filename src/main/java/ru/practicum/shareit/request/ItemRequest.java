package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "itemRequests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestorId")
    private User requestor; // пользователь создавший запрос

    @Column(name = "created") // в sql TIMESTAMP WITHOUT TIME ZONE
    private LocalDateTime created; // дата и время создания запроса

    public ItemRequest(String description, User requestor) {
        this.description = description;
        this.requestor = requestor;
    }
}
