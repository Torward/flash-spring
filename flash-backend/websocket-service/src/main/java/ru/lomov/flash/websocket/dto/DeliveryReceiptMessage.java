package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class DeliveryReceiptMessage {
    private String messageId;
    private String senderId;
    private String recipientId;
    private Long timestamp;
}
