package ru.dating.app.deckservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dating.app.deckservice.payload.ProfileResponse;
import ru.dating.app.deckservice.service.impl.DeckServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckServiceImpl deckService;

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> getDeck(@RequestParam UUID userId) {
        return ResponseEntity.ok(deckService.generateDeck(userId));
    }
}
