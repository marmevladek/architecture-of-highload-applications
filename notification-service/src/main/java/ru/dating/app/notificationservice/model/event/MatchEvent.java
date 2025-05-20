package ru.dating.app.notificationservice.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchEvent {
    private UUID user1Id;
    private UUID user2Id;
//    private String user1Email;
//    private String user2Email;
}
