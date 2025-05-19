package ru.dating.app.swipeservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dating.app.swipeservice.event.MatchEvent;
import ru.dating.app.swipeservice.mapper.SwipeMapper;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.repository.SwipeRepository;
import ru.dating.app.swipeservice.service.SwipeService;

import java.util.List;
import java.util.UUID;

@Service
public class SwipeServiceImpl implements SwipeService {

    private final SwipeRepository swipeRepository;

    public SwipeServiceImpl(SwipeRepository swipeRepository) {
        this.swipeRepository = swipeRepository;
    }

    @Transactional
    @Override
    public void processSwipe(SwipeRequest swipeRequest) {
        swipeRepository.save(SwipeMapper.mapToSwipe(swipeRequest));

        if (isMatch(swipeRequest.getUserId(), swipeRequest.getTargetId())) {
            MatchEvent event = new MatchEvent(swipeRequest.getUserId(), swipeRequest.getTargetId());
            System.out.println(event);
//            rabbitTemplate.convertAndSend("matches-exchange", "", event);
        }
    }

    private boolean isMatch(UUID userId, UUID targetId) {
        List<UUID> userLikes = swipeRepository.findLikedTargetIds(userId);
        List<UUID> targetLikes = swipeRepository.findLikedTargetIds(targetId);
        return userLikes.contains(targetId) && targetLikes.contains(userId);
    }
}
