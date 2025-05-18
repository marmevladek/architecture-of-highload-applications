package ru.dating.app.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dating.app.profileservice.model.Profile;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}
