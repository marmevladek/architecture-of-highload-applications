package ru.dating.app.swipeservice.mapper;

import ru.dating.app.swipeservice.model.Swipe;
import ru.dating.app.swipeservice.payload.SwipeRequest;

import java.time.LocalDateTime;

public class SwipeMapper {

    public static Swipe mapToSwipe(SwipeRequest swipeRequest) {
        return new Swipe(
                swipeRequest.getUserId(),
                swipeRequest.getTargetId(),
                swipeRequest.getDirection(),
                LocalDateTime.now()
        );
    }
}
