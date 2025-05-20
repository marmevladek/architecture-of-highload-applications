package ru.dating.app.profileservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    private String name;

    private int age;

    private String photoUrl;

    private Long chatId;

    private String telegramLink;

}
