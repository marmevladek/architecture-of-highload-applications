package ru.dating.app.swipeservice.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dating.app.swipeservice.dto.CoincidenceDTO;
import ru.dating.app.swipeservice.exception.TargetNotFoundException;
import ru.dating.app.swipeservice.external.client.DeckServiceClient;
import ru.dating.app.swipeservice.external.client.ProfileServiceClient;
import ru.dating.app.swipeservice.mapper.SwipeMapper;
import ru.dating.app.swipeservice.model.Swipe;
import ru.dating.app.swipeservice.model.enums.SwipeDirection;
import ru.dating.app.swipeservice.payload.ProfileResponse;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.repository.SwipeRepository;
import ru.dating.app.swipeservice.service.SwipeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SwipeServiceImpl implements SwipeService {

    private final DeckServiceClient deckServiceClient;
    private final ProfileServiceClient profileServiceClient;
    private final SwipeRepository swipeRepository;
    private final RedisTemplate<String, List<ProfileResponse>> redisTemplate;
    private final RabbitTemplate rabbitTemplate;


    public SwipeServiceImpl(DeckServiceClient deckServiceClient, ProfileServiceClient profileServiceClient, SwipeRepository swipeRepository, RedisTemplate<String, List<ProfileResponse>> redisTemplate, RabbitTemplate rabbitTemplate) {
        this.deckServiceClient = deckServiceClient;
        this.profileServiceClient = profileServiceClient;
        this.swipeRepository = swipeRepository;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    @Override
    public void processSwipe(SwipeRequest swipeRequest) {

        List<ProfileResponse> deck  = redisTemplate.opsForValue().get(swipeRequest.getSwiperId().toString());

//        if (deck == null || deck.isEmpty()) {
//            deck = deckServiceClient.getDeck(swipeRequest.getSwiperId());
//        }

        boolean targetInDeck = deck.stream()
                .anyMatch(profile -> profile.getId().equals(swipeRequest.getTargetId()));

        if (targetInDeck) {
            UUID swiperId = swipeRequest.getSwiperId();
            UUID targetId = swipeRequest.getTargetId();

            Swipe savedSwipe;

            Optional<Swipe> existing = swipeRepository.findSwipeBySwiperIdAndTargetId(targetId, swiperId);

            if (existing.isPresent()) {

                Swipe swipe = existing.get();
                swipe.setDirection2(swipeRequest.getDirection());
                savedSwipe = swipeRepository.save(swipe);

                if (isCoincidence(savedSwipe)) {

                    ProfileResponse swiperProfile = null;
                    ProfileResponse targetProfile = null;

                    for (ProfileResponse profile : deck) {
                        if (profile.getId().equals(targetId)) targetProfile = profile;
                    }

                    swiperProfile = profileServiceClient.getProfileById(swiperId);


                    if (swiperProfile != null && targetProfile != null) {

                        rabbitTemplate.convertAndSend(
                                "match-exchange",
                                "match-exchange.coincidence",
                                new CoincidenceDTO(
                                        targetId,
                                        targetProfile.getName(),
                                        targetProfile.getAge(),
                                        swiperProfile.getChatId(),
                                        targetProfile.getTelegramLink())
                        );

                        rabbitTemplate.convertAndSend(
                                "match-exchange",
                                "match-exchange.coincidence",
                                new CoincidenceDTO(
                                        swiperId,
                                        swiperProfile.getName(),
                                        swiperProfile.getAge(),
                                        targetProfile.getChatId(),
                                        swiperProfile.getTelegramLink()
                                )
                        );

                    }
                }


            } else {
                Swipe swipe = SwipeMapper.mapToSwipe(swipeRequest);
                swipe.setDirection1(swipeRequest.getDirection());
                savedSwipe = swipeRepository.save(swipe);
            }
            List<ProfileResponse> updatedDeck = deck.stream()
                    .filter(profileResponse -> !profileResponse.getId().equals(savedSwipe.getTargetId()))
                    .toList();

            redisTemplate.opsForValue().set(swiperId.toString(), updatedDeck);
        } else {
            throw new TargetNotFoundException("Target user not found", "NOT_FOUND", 404);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<UUID> getSwipedUserIds(UUID userId) {
        return swipeRepository.findSwipedUserIds(userId);
    }

    @Override
    public List<ProfileResponse> getDeck(UUID userId) {
        return deckServiceClient.getDeck(userId);
    }

    private boolean isCoincidence(Swipe swipe) {
        return swipe.getDirection1().equals(SwipeDirection.RIGHT) && swipe.getDirection2().equals(SwipeDirection.RIGHT);
    }
}
