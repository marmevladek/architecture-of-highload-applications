package ru.dating.app.profileservice.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.dating.app.profileservice.mapper.ProfileMapper;
import ru.dating.app.profileservice.model.Profile;
import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;
import ru.dating.app.profileservice.repository.ProfileRepository;
import ru.dating.app.profileservice.service.ProfileService;

import java.util.UUID;

@Service
@CacheConfig(cacheNames = "profiles")
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final RabbitTemplate rabbitTemplate;

    public ProfileServiceImpl(ProfileRepository profileRepository, RabbitTemplate rabbitTemplate) {
        this.profileRepository = profileRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Cacheable(key = "#id")
    @Override
    public ProfileResponse getProfileById(UUID id) {
        return ProfileMapper.mapToProfileResponse(profileRepository.findById(id).orElseThrow());
    }

    @CachePut(key = "#result.id")
    @Override
    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        Profile savedProfile = profileRepository.save(ProfileMapper.mapToProfile(profileRequest));
        return ProfileMapper.mapToProfileResponse(savedProfile);
    }
}
