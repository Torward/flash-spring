package ru.lomov.flashbackend.dto;

import lombok.Data;

@Data
public class CreateCommentReplyDto {
    private String postId;
    private String userId;
    private String content;
    private String parentCommentId; // ID of the parent comment being replied to
}
