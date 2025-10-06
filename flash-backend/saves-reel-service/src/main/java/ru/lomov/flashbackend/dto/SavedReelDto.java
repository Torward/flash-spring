package ru.lomov.flashbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedReelDto {
    private String saveId;
    private String userId;
    private String reelId;
    private LocalDateTime savedAt;
}
