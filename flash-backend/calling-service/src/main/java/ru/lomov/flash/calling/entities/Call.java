package ru.lomov.flash.calling.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Call {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String callerId; // ID of the user initiating the call

    @Column(nullable = false)
    private String receiverId; // ID of the user receiving the call

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallType type; // VOICE_CALL, VIDEO_CALL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallStatus status; // INITIATED, RINGING, CONNECTED, ENDED, MISSED, REJECTED

    @Column
    private String roomId; // WebRTC room identifier

    @Column
    private LocalDateTime startedAt; // When the call was connected

    @Column
    private LocalDateTime endedAt; // When the call ended

    @Column
    private Long duration; // Call duration in seconds

    @Column
    private String endReason; // REJECTED, NO_ANSWER, CONNECTION_LOST, USER_ENDED, etc.

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
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
