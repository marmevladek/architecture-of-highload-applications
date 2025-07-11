package ru.dating.app.profileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dating.app.profileservice.payload.ProfileRequest;
import ru.dating.app.profileservice.payload.ProfileResponse;
import ru.dating.app.profileservice.service.ProfileService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.updateProfile(profileRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> getProfiles(
            @RequestParam UUID excludeUserId,
            @RequestParam(defaultValue = "300") int limit
    ) {
        return ResponseEntity.ok(profileService.findAllExcludeUserId(excludeUserId, limit));
    }

    @GetMapping("/allProfiles")
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }
}
