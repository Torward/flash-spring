package ru.lomov.flash.reels.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReelResponse {
    private Long id;
    private String userId;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer durationSeconds;
    private List<String> hashtags;
    private Integer likesCount;
    private Integer commentsCount;
    private Integer viewsCount;
    private Integer sharesCount;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
