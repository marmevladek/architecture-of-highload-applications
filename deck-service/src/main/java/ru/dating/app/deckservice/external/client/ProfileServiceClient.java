package ru.dating.app.deckservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dating.app.deckservice.config.FeignRetryConfig;
import ru.dating.app.deckservice.payload.ProfileResponse;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "profile-service", url = "${profile-service.url}", configuration = FeignRetryConfig.class)
public interface ProfileServiceClient {

    @GetMapping("/api/profiles")
    List<ProfileResponse> getProfiles(@RequestParam("excludeUserId") UUID excludeUserId);

    @GetMapping("/api/profiles/allProfiles")
    List<ProfileResponse> getAllProfiles();
}
