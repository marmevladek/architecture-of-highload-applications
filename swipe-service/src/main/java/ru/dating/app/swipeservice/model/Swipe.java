package ru.dating.app.swipeservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dating.app.swipeservice.model.enums.SwipeDirection;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "swipes")
@Getter
@Setter
@NoArgsConstructor
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "target_id")
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    private SwipeDirection direction;

    private LocalDateTime timestamp;

    public Swipe(UUID userId, UUID targetId, SwipeDirection direction, LocalDateTime timestamp) {
        this.userId = userId;
        this.targetId = targetId;
        this.direction = direction;
        this.timestamp = timestamp;
    }
}
