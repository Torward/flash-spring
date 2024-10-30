package ru.lomov.flashbackend.dto;

import java.time.LocalDateTime;

public class StatusDto {
    private Long id;
    private UserDto user;
    private LocalDateTime createdAt;
    private CommentDto comment;
    private String content;
    private int totalLikes;
    private boolean isLiked;
}
