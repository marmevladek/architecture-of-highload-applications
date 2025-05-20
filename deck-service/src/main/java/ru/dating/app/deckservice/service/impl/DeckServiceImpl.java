package ru.dating.app.deckservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dating.app.deckservice.external.client.ProfileServiceClient;
import ru.dating.app.deckservice.payload.ProfileResponse;
import ru.dating.app.deckservice.service.DeckService;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class DeckServiceImpl implements DeckService {

    private final ProfileServiceClient profileServiceClient;
    private final RedisTemplate<String, List<ProfileResponse>> redisTemplate;


    public DeckServiceImpl(ProfileServiceClient profileServiceClient, RedisTemplate<String, List<ProfileResponse>> redisTemplate) {
        this.profileServiceClient = profileServiceClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<ProfileResponse> generateDeck(UUID userId) {
        List<ProfileResponse> deck = profileServiceClient.getProfiles(userId);

        redisTemplate.opsForValue().set(userId.toString(), deck, Duration.ofHours(12));
        return deck;
    }

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)
    public void refreshAllDecks() {
        List<ProfileResponse> allProfiles = profileServiceClient.getAllProfiles();
        for (ProfileResponse profile : allProfiles) {
            generateDeck(profile.getId());
        }
    }


}
