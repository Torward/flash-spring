package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class TypingIndicatorMessage {
    private String chatId;
    private String userId;
    private boolean isTyping;
}
