package ru.dating.app.swipeservice.service;

import ru.dating.app.swipeservice.payload.ProfileResponse;
import ru.dating.app.swipeservice.payload.SwipeRequest;

import java.util.List;
import java.util.UUID;

public interface SwipeService {

    void processSwipe(SwipeRequest swipeRequest);

    List<UUID> getSwipedUserIds(UUID userId);

    List<ProfileResponse> getDeck(UUID userId);
}
