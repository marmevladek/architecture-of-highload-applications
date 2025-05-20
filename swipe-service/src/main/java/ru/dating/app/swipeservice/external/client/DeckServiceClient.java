package ru.dating.app.swipeservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dating.app.swipeservice.payload.ProfileResponse;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "deck-service", url = "${deck-service.url}")
public interface DeckServiceClient {
    @GetMapping("/api/deck")
    List<ProfileResponse> getDeck(@RequestParam UUID userId);

}
