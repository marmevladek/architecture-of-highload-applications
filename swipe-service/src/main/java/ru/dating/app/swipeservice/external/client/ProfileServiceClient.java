package ru.dating.app.swipeservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.dating.app.swipeservice.payload.ProfileResponse;

import java.util.UUID;

@FeignClient(name = "profile-service", url = "${profile-service.url}")
public interface ProfileServiceClient {

    @GetMapping("/api/profiles/{id}")
    ProfileResponse getProfileById(@PathVariable("id") UUID id);
}
