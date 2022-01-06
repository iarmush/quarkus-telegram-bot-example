package ru.my.bot.service;

import javax.inject.Singleton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.my.bot.config.TelegramBotConfig;

public class TelegramLongPollingBotImpl {

    private TelegramBotConfig telegramBotConfig;

    public TelegramLongPollingBotImpl(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Singleton
    public TelegramLongPollingBot telegramLongPollingBot() {
        return new TelegramLongPollingBot() {
            @Override
            public String getBotToken() {
                return telegramBotConfig.token();
            }

            @Override
            public void onUpdateReceived(Update update) {
                try {
                    if (update.hasMessage() && update.getMessage().getText() != null) {
                        execute(
                            new SendMessage(String.valueOf(update.getMessage().getChatId()),
                                "from bot: " + update.getMessage().getText()));
                    } else {
                        execute(new SendMessage(String.valueOf(update.getMessage().getChatId()), "Message from bot"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getBotUsername() {
                return telegramBotConfig.username();
            }
        };
    }
}
