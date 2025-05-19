package ru.dating.app.swipeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dating.app.swipeservice.model.Swipe;

import java.util.List;
import java.util.UUID;

public interface SwipeRepository extends JpaRepository<Swipe, UUID> {
    @Query("SELECT s.targetId FROM Swipe s WHERE s.userId = :userId AND s.direction = 'RIGHT'")
    List<UUID> findLikedTargetIds(@Param("userId") UUID userId);
}
