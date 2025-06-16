package ru.dating.app.deckservice.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dating.app.deckservice.external.client.ProfileServiceClient;
import ru.dating.app.deckservice.payload.ProfileResponse;
import ru.dating.app.deckservice.service.DeckService;
import ru.dating.app.deckservice.utils.SwipeLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class DeckServiceImpl implements DeckService {

    private final ProfileServiceClient profileServiceClient;
    private final SwipeLogger swipeLogger;
    private final RedisTemplate<String, List<ProfileResponse>> redisTemplate;

    private final Random random = new Random();


    public DeckServiceImpl(ProfileServiceClient profileServiceClient, SwipeLogger swipeLogger, RedisTemplate<String, List<ProfileResponse>> redisTemplate) {
        this.profileServiceClient = profileServiceClient;
        this.swipeLogger = swipeLogger;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<ProfileResponse> generateDeck(UUID userId) {
        List<ProfileResponse> deck = profileServiceClient.getProfiles(userId);


        redisTemplate.opsForValue().set(userId.toString(), deck, Duration.ofHours(12));

        for (ProfileResponse profile : deck) {
            String direction = random.nextBoolean() ? "RIGHT" : "LEFT";
            swipeLogger.log(userId, profile.getId(), direction);
        }

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
