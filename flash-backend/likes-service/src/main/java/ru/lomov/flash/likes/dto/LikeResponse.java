package ru.lomov.flash.likes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LikeResponse {
    private String id;
    private String postId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
