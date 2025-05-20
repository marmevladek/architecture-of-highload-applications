package ru.dating.app.profileservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "chat_id")
    private Long chatId;

    private String telegramLink;

    public Profile(String name, int age, String photoUrl, Long chatId, String telegramLink) {
        this.name = name;
        this.age = age;
        this.photoUrl = photoUrl;
        this.chatId = chatId;
        this.telegramLink = telegramLink;
    }
}
