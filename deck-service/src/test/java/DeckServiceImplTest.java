import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import ru.dating.app.deckservice.external.client.ProfileServiceClient;
import ru.dating.app.deckservice.payload.ProfileResponse;
import ru.dating.app.deckservice.service.impl.DeckServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeckServiceImplTest {

    @Mock private ProfileServiceClient profileServiceClient;
    @Mock private RedisTemplate<String, List<ProfileResponse>> redisTemplate;
    @Mock private ValueOperations<String, List<ProfileResponse>> valueOperations;

    @InjectMocks
    private DeckServiceImpl deckService;

    @Test
    void generateDeck_shouldStoreInRedisAndReturnDeck() {
        UUID userId = UUID.randomUUID();
        List<ProfileResponse> profiles = List.of(new ProfileResponse(userId, "Anna", 22, "", 99L, "link"));

        when(profileServiceClient.getProfiles(userId)).thenReturn(profiles);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        List<ProfileResponse> result = deckService.generateDeck(userId);

        assertEquals(1, result.size());
        verify(valueOperations).set(eq(userId.toString()), eq(profiles), any());
    }
}