package ru.lomov.flashbackend.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentReplyRequest {
    private String content;
    private String image;
    private Long commentId;
    private LocalDateTime createdAt;
}
