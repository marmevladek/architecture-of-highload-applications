package ru.dating.app.swipeservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dating.app.swipeservice.exception.TargetNotFoundException;
import ru.dating.app.swipeservice.payload.ErrorResponse;
import ru.dating.app.swipeservice.payload.MessageResponse;
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
    private static final Logger logger = LoggerFactory.getLogger(SwipeController.class);

    @PostMapping
    public ResponseEntity<?> registerSwipe(@RequestBody SwipeRequest swipeRequest) {
        logger.info("Swipe request: {}", swipeRequest);
        try {
            swipeService.processSwipe(swipeRequest);
            return ResponseEntity.ok().build();
        } catch (TargetNotFoundException e) {
            return new ResponseEntity<>(new MessageResponse("Target User not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
