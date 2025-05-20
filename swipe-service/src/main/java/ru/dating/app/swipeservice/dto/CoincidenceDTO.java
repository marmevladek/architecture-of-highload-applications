package ru.dating.app.swipeservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CoincidenceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID targetId;
    private String targetName;
    private int targetAge;
    private Long swiperChatId;
    private String targetTelegramLink;

    public CoincidenceDTO(UUID targetId, String targetName, int targetAge, Long swiperChatId, String targetTelegramLink) {
        this.targetId = targetId;
        this.targetName = targetName;
        this.targetAge = targetAge;
        this.swiperChatId = swiperChatId;
        this.targetTelegramLink = targetTelegramLink;
    }
}
