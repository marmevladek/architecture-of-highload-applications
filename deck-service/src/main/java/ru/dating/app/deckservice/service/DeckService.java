package ru.dating.app.deckservice.service;

import ru.dating.app.deckservice.payload.ProfileResponse;

import java.util.List;
import java.util.UUID;

public interface DeckService {

    List<ProfileResponse> generateDeck(UUID userId);

}
