package ru.dating.app.swipeservice.service;

import ru.dating.app.swipeservice.payload.SwipeRequest;

public interface SwipeService {

    void processSwipe(SwipeRequest swipeRequest);

}
