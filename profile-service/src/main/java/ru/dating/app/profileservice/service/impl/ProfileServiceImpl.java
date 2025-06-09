package ru.dating.app.profileservice.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dating.app.profileservice.mapper.ProfileMapper;
import ru.dating.app.profileservice.model.Profile;
import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;
import ru.dating.app.profileservice.repository.ProfileRepository;
import ru.dating.app.profileservice.service.ProfileService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "profiles")
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    @Override
    public ProfileResponse getProfileById(UUID id) {
        return ProfileMapper.mapToProfileResponse(profileRepository.findById(id).orElseThrow());
    }

    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        Profile savedProfile = profileRepository.save(ProfileMapper.mapToProfile(profileRequest));
        return ProfileMapper.mapToProfileResponse(savedProfile);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileResponse> findAllExcludeUserId(UUID excludeUserId, int limit) {
        return profileRepository.findAllExcludeUserId(excludeUserId, limit).stream()
                .map(ProfileMapper::mapToProfileResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileResponse> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(ProfileMapper::mapToProfileResponse)
                .collect(Collectors.toList());
    }
}
