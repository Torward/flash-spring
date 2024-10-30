package ru.lomov.flashbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.CommentNotFoundException;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.request.CommentReplyRequest;
import ru.lomov.flashbackend.request.PostReplyRequest;
import ru.lomov.flashbackend.services.CommentService;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.CommentDtoMapper;
import ru.lomov.flashbackend.utils.PostDtoMapper;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @GetMapping()
    public List<Comment> getComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping()
    public CommentDto createComment(@RequestBody Comment req, @RequestHeader("Authorization") String jwt) {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Comment comment = commentService.createComment(req, user);
        CommentDto commentDto = CommentDtoMapper.commentToDto(comment, user);
        return commentDto;
    }
    @PostMapping("/reply")
    public CommentDto createReply(@RequestBody CommentReplyRequest req, @RequestHeader("Authorization") String jwt) throws CommentNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
       Comment comment = commentService.createReply(req, user);
        CommentDto commentDto = CommentDtoMapper.commentToDto(comment, user);
        return commentDto;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(commentService.findById(id));
    }

}