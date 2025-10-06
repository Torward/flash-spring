package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class UserStatusMessage {
    private String userId;
    private String status; // online, offline, away
    private Long timestamp;
}
