package ru.dating.app.profileservice.service;

import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;

import java.util.UUID;

public interface ProfileService {

    ProfileResponse getProfileById(UUID id);

    ProfileResponse updateProfile(ProfileRequest profileRequest);
}
