package ru.dating.app.notificationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dating.app.notificationservice.dto.CoincidenceDTO;

@Service
public class TelegramBotService extends TelegramLongPollingBot {



    @Value("${telegram.bot.name}")
    private String botName;

    public TelegramBotService(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendNotification(CoincidenceDTO coincidenceDTO) {
        sendMessage(coincidenceDTO.getSwiperChatId(), "Привет!\n" +
                "У тебя совпадение с " + coincidenceDTO.getTargetName() + ": " + coincidenceDTO.getTargetTelegramLink());
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        executeMessage(sendMessage);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
