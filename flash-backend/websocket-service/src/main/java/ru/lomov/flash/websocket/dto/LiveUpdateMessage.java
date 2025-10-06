package ru.lomov.flash.websocket.dto;

import lombok.Data;

@Data
public class LiveUpdateMessage {
    private String streamId;
    private String type; // viewer_count, status, etc.
    private Object data;
    private Long timestamp;
}
