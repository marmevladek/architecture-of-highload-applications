package ru.dating.app.swipeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.service.SwipeService;

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
}
