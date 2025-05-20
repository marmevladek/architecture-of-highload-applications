package ru.dating.app.swipeservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponse {

    private UUID id;

    private String name;

    private int age;

    private String photoUrl;

    private Long chatId;

    private String telegramLink;

    public ProfileResponse(UUID id, String name, int age, String photoUrl, Long chatId, String telegramLink) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoUrl = photoUrl;
        this.chatId = chatId;
        this.telegramLink = telegramLink;
    }
}
