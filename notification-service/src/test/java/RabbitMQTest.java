import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.dating.app.notificationservice.dto.CoincidenceDTO;
import ru.dating.app.notificationservice.listener.RabbitMQListener;
import ru.dating.app.notificationservice.service.TelegramBotService;

import static org.mockito.Mockito.*;

class RabbitMQTest {

    private TelegramBotService telegramBotService;
    private RabbitMQListener rabbitMQListener;

    @BeforeEach
    void setUp() {
        telegramBotService = mock(TelegramBotService.class);
        rabbitMQListener = new RabbitMQListener(telegramBotService);
    }

    @Test
    void handleMatch_shouldCallSendNotification() {
        CoincidenceDTO dto = new CoincidenceDTO();
        dto.setSwiperChatId(123456789L);
        dto.setTargetName("Аня");
        dto.setTargetTelegramLink("https://t.me/anya");

        rabbitMQListener.handleMatch(dto);

        verify(telegramBotService, times(1)).sendNotification(dto);
    }
}