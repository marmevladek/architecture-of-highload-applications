import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dating.app.swipeservice.controller.SwipeController;
import ru.dating.app.swipeservice.model.enums.SwipeDirection;
import ru.dating.app.swipeservice.payload.SwipeRequest;
import ru.dating.app.swipeservice.service.SwipeService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SwipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwipeService swipeService;

    @InjectMocks
    private SwipeController swipeController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(swipeController).build();
    }

    @Test
    void registerSwipe_shouldReturnOk() throws Exception {
        SwipeRequest request = new SwipeRequest(UUID.randomUUID(), UUID.randomUUID(), SwipeDirection.RIGHT);

        mockMvc.perform(post("/api/swipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(swipeService).processSwipe(any(SwipeRequest.class));
    }

    @Test
    void getSwipedUsers_shouldReturnUserIds() throws Exception {
        UUID userId = UUID.randomUUID();
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());

        when(swipeService.getSwipedUserIds(userId)).thenReturn(ids);

        mockMvc.perform(get("/api/swipes/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}