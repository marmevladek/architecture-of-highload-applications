package ru.dating.app.profileservice.service;

import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;

import java.util.List;
import java.util.UUID;

public interface ProfileService {

    ProfileResponse getProfileById(UUID id);

    ProfileResponse updateProfile(ProfileRequest profileRequest);

    List<ProfileResponse> findAllExcludeUserId(UUID excludeUserId, int limit);

    List<ProfileResponse> getAllProfiles();
}
