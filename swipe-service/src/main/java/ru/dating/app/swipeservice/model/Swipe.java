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

    @Column(name = "swiper_id")
    private UUID swiperId;

    @Column(name = "target_id")
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_1")
    private SwipeDirection direction1;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_2")
    private SwipeDirection direction2;

    public Swipe(UUID userId, UUID targetId) {
        this.swiperId = userId;
        this.targetId = targetId;
    }
}
