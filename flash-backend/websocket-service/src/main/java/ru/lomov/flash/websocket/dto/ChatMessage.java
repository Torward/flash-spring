package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class ChatMessage {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    private MessageType type;
    private String content;
    private String sender;
    private Long timestamp;
}
