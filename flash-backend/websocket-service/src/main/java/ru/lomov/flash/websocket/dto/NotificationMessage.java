package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class NotificationMessage {
    private String title;
    private String message;
    private String type;
    private String userId;
    private Long timestamp;
}
