package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class PrivateMessage {
    private String content;
    private String sender;
    private String recipient;
    private Long timestamp;
}
