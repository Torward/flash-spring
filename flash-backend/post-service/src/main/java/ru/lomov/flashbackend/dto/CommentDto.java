package ru.lomov.flashbackend.dto;

import lombok.Data;
import ru.lomov.flashbackend.entities.Comment;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CommentDto {
    private String commentId;
    private String postId;
    private String userId;
    private String content;
    private String parentCommentId;
    private Set<String> replyIds = new HashSet<>();
    private int replyCount;
    private int likeCount;
    private boolean isEdited;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public CommentDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.postId = comment.getPostId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommentId();
        this.replyIds = comment.getReplyIds();
        this.replyCount = comment.getReplyCount();
        this.likeCount = comment.getLikeCount();
        this.isEdited = comment.isEdited();
        this.isDeleted = comment.isDeleted();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.deletedAt = comment.getDeletedAt();
    }
}
