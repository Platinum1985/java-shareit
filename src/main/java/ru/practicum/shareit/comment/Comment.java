package ru.practicum.shareit.comment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.model.Item;


import java.time.Instant;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId")
    private Item item;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "created")
    private Instant created;

}
