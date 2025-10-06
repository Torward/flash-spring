package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class ReadReceiptMessage {
    private String messageId;
    private String senderId;
    private String readerId;
    private Long timestamp;
}
