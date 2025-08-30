package ru.lomov.flashbackend.story.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoryResponse {
    private String id;
    private String userId;
    private String type;
    private String image;
    private String video;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Integer views;
    private String text;
    private String location;
    private String privacy;
    private String background;
    private String font;
    private String color;
    
    // Additional Firebase fields
    private String hashtags;
    private String mentions;
    private String link;
    private Integer duration;
    private String aspectRatio;
    private String filter;
    private String music;
    private String productTag;
    private String locationId;
    private String pollQuestion;
    private String pollOptions;
    private String quizQuestion;
    private String quizAnswer;
    private String emojiSlider;
    private String question;
    private LocalDateTime countdownEnd;
    
    // Firebase statistics
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;
    private Boolean isArchived;
    private Boolean isHighlight;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
