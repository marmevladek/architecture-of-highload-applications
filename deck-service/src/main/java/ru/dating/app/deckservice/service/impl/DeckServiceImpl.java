package ru.dating.app.deckservice.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dating.app.deckservice.external.client.ProfileServiceClient;
import ru.dating.app.deckservice.payload.ProfileResponse;
import ru.dating.app.deckservice.service.DeckService;

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
    private final RedisTemplate<String, List<ProfileResponse>> redisTemplate;


    public DeckServiceImpl(ProfileServiceClient profileServiceClient, RedisTemplate<String, List<ProfileResponse>> redisTemplate) {
        this.profileServiceClient = profileServiceClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<ProfileResponse> generateDeck(UUID userId) {
        List<ProfileResponse> deck = profileServiceClient.getProfiles(userId);


        redisTemplate.opsForValue().set(userId.toString(), deck, Duration.ofHours(12));

        Path csvPath = Paths.get("infrastructure-services", "tsung", "test-swipes.csv");

        Random random = new Random();

        try {
            if (Files.notExists(csvPath)) {
                Files.createFile(csvPath);
                Files.write(csvPath, List.of("swiper_id,target_id,direction"), StandardOpenOption.APPEND);
            }

            List<String> linesToAppend = deck.stream()
                    .map(profile -> {
                        String direction = random.nextBoolean() ? "RIGHT" : "LEFT";
                        return userId + "," + profile.getId() + "," + direction;
                    })
                    .toList();

            Files.write(csvPath, linesToAppend, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write deck file.", e);
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
