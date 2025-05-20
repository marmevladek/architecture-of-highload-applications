package ru.dating.app.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dating.app.profileservice.model.Profile;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @Query(
            value = "SELECT * FROM profiles p WHERE p.id != :excludeUserId ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true
    )
    List<Profile> findAllExcludeUserId(
            @Param("excludeUserId") UUID excludeUserId,
            @Param("limit") int limit
    );
}
