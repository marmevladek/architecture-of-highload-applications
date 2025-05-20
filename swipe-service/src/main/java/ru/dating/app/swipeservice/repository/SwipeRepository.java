package ru.dating.app.swipeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dating.app.swipeservice.model.Swipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SwipeRepository extends JpaRepository<Swipe, UUID> {
    Optional<Swipe> findSwipeBySwiperIdAndTargetId(UUID swiperId, UUID targetId);

    @Query("SELECT s.targetId FROM Swipe s WHERE s.swiperId = :userId")
    List<UUID> findSwipedUserIds(@Param("userId") UUID userId);
}
