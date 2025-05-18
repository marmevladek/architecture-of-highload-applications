package ru.dating.app.profileservice.mapper;

import ru.dating.app.profileservice.model.Profile;
import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;

public class ProfileMapper {

    public static Profile mapToProfile(ProfileRequest profileRequest) {
        return new Profile(
                profileRequest.getName(),
                profileRequest.getAge(),
                profileRequest.getPhotoUrl()
        );
    }

    public static ProfileResponse mapToProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getName(),
                profile.getAge(),
                profile.getPhotoUrl()
        );
    }
}
