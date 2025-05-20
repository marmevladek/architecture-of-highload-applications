package ru.dating.app.notificationservice.config.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.dating.app.notificationservice.service.TelegramBotService;

@Component
public class TelegramBotInit {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInit.class);

    private final TelegramBotService telegramBotService;

    public TelegramBotInit(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(telegramBotService);
        } catch (Exception e) {
            logger.error("Error while initializing bot: {}", e.getMessage());
        }
    }
}
