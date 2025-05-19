package ru.dating.app.swipeservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dating.app.swipeservice.model.enums.SwipeDirection;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwipeRequest {
    private UUID userId;
    private UUID targetId;
    private SwipeDirection direction;
}
