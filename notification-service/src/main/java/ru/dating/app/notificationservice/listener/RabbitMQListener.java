package ru.dating.app.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.dating.app.notificationservice.dto.CoincidenceDTO;
import ru.dating.app.notificationservice.service.TelegramBotService;

@Component
public class RabbitMQListener {

    private final TelegramBotService telegramBotService;


    public RabbitMQListener(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @RabbitListener(queues = "coincidence-queue")
    public void handleMatch(CoincidenceDTO coincidenceDTO) {
        telegramBotService.sendNotification(coincidenceDTO);
    }

}
