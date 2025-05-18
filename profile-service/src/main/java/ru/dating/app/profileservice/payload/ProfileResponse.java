package ru.dating.app.profileservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String name;

    private int age;

    private String photoUrl;

    public ProfileResponse(UUID id, String name, int age, String photoUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoUrl = photoUrl;
    }
}
