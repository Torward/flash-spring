package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.dto.CreateCommentReplyDto;
import ru.lomov.flashbackend.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        CommentDto comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable Long userId) {
        List<CommentDto> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(id, commentDto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getCommentCountByPostId(@PathVariable Long postId) {
        Long count = commentService.getCommentCountByPostId(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getCommentCountByUserId(@PathVariable Long userId) {
        Long count = commentService.getCommentCountByUserId(userId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{commentId}/like/{userId}")
    public ResponseEntity<CommentDto> likeComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        CommentDto updatedComment = commentService.likeComment(commentId, userId);
        return ResponseEntity.ok(updatedComment);
    }

    @PostMapping("/{commentId}/unlike/{userId}")
    public ResponseEntity<CommentDto> unlikeComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        CommentDto updatedComment = commentService.unlikeComment(commentId, userId);
        return ResponseEntity.ok(updatedComment);
    }

    // Reply endpoints
    @PostMapping("/reply")
    public ResponseEntity<CommentDto> createCommentReply(@RequestBody CreateCommentReplyDto replyDto) {
        CommentDto createdReply = commentService.createCommentReply(replyDto);
        return ResponseEntity.ok(createdReply);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentDto>> getCommentReplies(@PathVariable Long commentId) {
        List<CommentDto> replies = commentService.getCommentReplies(commentId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/{commentId}/reply-count")
    public ResponseEntity<Long> getCommentReplyCount(@PathVariable Long commentId) {
        Long count = commentService.getCommentReplyCount(commentId);
        return ResponseEntity.ok(count);
    }
}
