package ru.dating.app.deckservice.utils;

import java.util.UUID;

public interface SwipeLogger {
    void log(UUID swiperId, UUID targetId, String direction);
}
