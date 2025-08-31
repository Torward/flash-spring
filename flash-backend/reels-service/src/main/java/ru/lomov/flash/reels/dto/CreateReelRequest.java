package ru.lomov.flash.reels.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateReelRequest {
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer durationSeconds;
    private List<String> hashtags;
    private Boolean isPublic = true;
}
