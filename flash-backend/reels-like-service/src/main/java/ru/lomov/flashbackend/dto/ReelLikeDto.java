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
public class ReelLikeDto {
    private String likeId;
    private String reelId;
    private String userId;
    private LocalDateTime likedAt;
}
