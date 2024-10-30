package ru.lomov.flashbackend.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostReplyRequest {
    private Long replyId;
    private String content;
    private String image;
    private Long postId;
    private LocalDateTime createdAt;
}
