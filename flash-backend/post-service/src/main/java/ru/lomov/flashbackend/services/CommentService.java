package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.dto.CreateCommentReplyDto;
import ru.lomov.flashbackend.entities.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentDto getCommentById(Long id);
    List<CommentDto> getCommentsByPostId(Long postId);
    List<CommentDto> getCommentsByUserId(Long userId);
    CommentDto updateComment(Long id, CommentDto commentDto);
    void deleteComment(Long id);
    Long getCommentCountByPostId(Long postId);
    Long getCommentCountByUserId(Long userId);
    CommentDto likeComment(Long commentId, Long userId);
    CommentDto unlikeComment(Long commentId, Long userId);

    // Reply management methods
    CommentDto createCommentReply(CreateCommentReplyDto replyDto);
    List<CommentDto> getCommentReplies(Long commentId);
    Long getCommentReplyCount(Long commentId);
}
