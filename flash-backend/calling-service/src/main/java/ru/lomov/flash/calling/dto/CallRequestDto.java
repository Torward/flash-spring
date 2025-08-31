package ru.lomov.flash.calling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallRequestDto {

    @NotBlank(message = "Caller ID is required")
    private String callerId;

    @NotBlank(message = "Receiver ID is required")
    private String receiverId;

    @NotNull(message = "Call type is required")
    private CallType type;

    public enum CallType {
        VOICE_CALL,
        VIDEO_CALL
    }
}
