package ru.lomov.flash.calling.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallDto {

    private String id;
    private String callerId;
    private String receiverId;
    private CallType type;
    private CallStatus status;
    private String roomId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Long duration;
    private String endReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum CallType {
        VOICE_CALL,
        VIDEO_CALL
    }

    public enum CallStatus {
        INITIATED,
        RINGING,
        CONNECTED,
        ENDED,
        MISSED,
        REJECTED,
        CANCELLED
    }
}
