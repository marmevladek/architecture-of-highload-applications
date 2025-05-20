package ru.dating.app.swipeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dating.app.swipeservice.payload.ProfileResponse;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.service.SwipeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/swipes")
@RequiredArgsConstructor
public class SwipeController {
    private final SwipeService swipeService;

    @PostMapping
    public ResponseEntity<Void> registerSwipe(@RequestBody SwipeRequest swipeRequest) {
        swipeService.processSwipe(swipeRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> generateDeck(@RequestParam UUID swipeId) {
        return null;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<UUID>> getSwipedUsers(@PathVariable UUID userId) {
        return ResponseEntity.ok(swipeService.getSwipedUserIds(userId));
    }
}
