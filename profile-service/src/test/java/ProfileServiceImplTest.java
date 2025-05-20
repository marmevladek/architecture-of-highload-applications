import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.dating.app.profileservice.model.Profile;
import ru.dating.app.profileservice.payload.ProfileResponse;
import ru.dating.app.profileservice.repository.ProfileRepository;
import ru.dating.app.profileservice.service.impl.ProfileServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void getProfileById_shouldReturnProfile() {
        UUID id = UUID.randomUUID();
        Profile profile = new Profile("John", 25, "", 123L, "t.me/john");

        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        ProfileResponse result = profileService.getProfileById(id);

        assertEquals("John", result.getName());
        assertEquals(25, result.getAge());
    }
}