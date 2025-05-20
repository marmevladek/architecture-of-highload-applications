package ru.dating.app.deckservice.payload;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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

}