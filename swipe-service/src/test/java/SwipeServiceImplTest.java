import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import ru.dating.app.swipeservice.model.Swipe;
import ru.dating.app.swipeservice.model.enums.SwipeDirection;
import ru.dating.app.swipeservice.payload.ProfileResponse;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.repository.SwipeRepository;
import ru.dating.app.swipeservice.service.impl.SwipeServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwipeServiceImplTest {

    @Mock private SwipeRepository swipeRepository;
    @Mock private RedisTemplate<String, List<ProfileResponse>> redisTemplate;
    @Mock private ValueOperations<String, List<ProfileResponse>> valueOperations;

    @InjectMocks
    private SwipeServiceImpl swipeService;

    private final UUID swiperId = UUID.randomUUID();
    private final UUID targetId = UUID.randomUUID();

    @Test
    void processSwipe_shouldSaveNewSwipeAndUpdateDeck() {
        SwipeRequest request = new SwipeRequest(swiperId, targetId, SwipeDirection.RIGHT);

        List<ProfileResponse> deck = List.of(new ProfileResponse(targetId, "Name", 20, "", 1L, "link"));

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(swiperId.toString())).thenReturn(deck);
        Mockito.when(swipeRepository.findSwipeBySwiperIdAndTargetId(targetId, swiperId)).thenReturn(Optional.empty());

        Mockito.when(swipeRepository.save(Mockito.any(Swipe.class))).thenAnswer(i -> i.getArgument(0));

        swipeService.processSwipe(request);

        Mockito.verify(swipeRepository, times(1)).save(Mockito.any(Swipe.class));
        Mockito.verify(valueOperations).set(eq(swiperId.toString()), anyList());
    }

    @Test
    void getSwipedUserIds_shouldReturnList() {
        List<UUID> swipedIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(swipeRepository.findSwipedUserIds(swiperId)).thenReturn(swipedIds);

        List<UUID> result = swipeService.getSwipedUserIds(swiperId);

        assertEquals(swipedIds, result);
    }
}