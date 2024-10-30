package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.CommentNotFoundException;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.CommentRepository;
import ru.lomov.flashbackend.request.CommentReplyRequest;
import ru.lomov.flashbackend.request.PostReplyRequest;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final UserService userService;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));
    }
    @Override
    public Comment createComment(Comment req, AppUser user) throws UserNotFoundException {
        Comment comment = new Comment();
        comment.setContent(req.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setMedia(req.getMedia());
        comment.setUser(user);
        comment.setReply(false);
        comment.setComment(true);
        return commentRepository.save(comment);
    }
    @Override
    public Comment createReply(CommentReplyRequest replyRequest, AppUser user) throws CommentNotFoundException {
        Comment replyFor = findById(replyRequest.getCommentId());
        Comment comment = new Comment();
        comment.setContent(replyRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setMedia(replyRequest.getImage());
        comment.setUser(user);
        comment.setReply(true);
        comment.setComment(false);
        comment.setReplyFor(replyFor);
        Comment savedReply = commentRepository.save(comment);
        comment.getReplyComments().add(savedReply);
       commentRepository.save(replyFor);
        return replyFor;
    }

    @Override
    public List<Comment> findByPostIdAndDeletedAtIsNull(Long postId) {
        return commentRepository.findByPostIdAndDeletedAtIsNull(postId);
    }

    @Override
    public List<Comment> findByPostIdAndDeletedAtBetween(Long postId, LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.findByPostIdAndDeletedAtBetween(postId, startDate, endDate);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> findByAuthor(AppUser author) {
        return commentRepository.findByAuthor(userService.findUserById(author.getUserId()));
    }

    @Override
    public List<Comment> findByContentContains(String content) {
        return commentRepository.findByContentContains(content);
    }

    @Override
    public List<Comment> findByLikesCountGreaterThan(int likesCount) {
        return commentRepository.findByLikesCountGreaterThan(likesCount);
    }

    @Override
    public List<Comment> findByCommentsCountGreaterThan(int commentsCount) {
        return commentRepository.findByReplyCommentsCountGreaterThan(commentsCount);
    }

    @Override
    public List<Comment> findByDateCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public List<Comment> findByDateModifiedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.findByModifiedAtBetween(startDate, endDate);
    }

    @Override
    public List<Comment> findByDateDeletedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.findByDeletedAtBetween(startDate, endDate);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> findByDateCreatedBefore(LocalDateTime date) {
        return commentRepository.findByCreatedAtBefore(date);
    }

    @Override
    public List<Comment> findByDateCreatedAfter(LocalDateTime date) {
        return commentRepository.findByCreatedAtAfter(date);
    }

    @Override
    public List<Comment> findByDateModifiedBefore(LocalDateTime date) {
        return commentRepository.findByModifiedAtBefore(date);
    }

    @Override
    public List<Comment> findByDateModifiedAfter(LocalDateTime date) {
        return commentRepository.findByModifiedAtAfter(date);
    }

    @Override
    public List<Comment> findByDateDeletedBefore(LocalDateTime date) {
        return commentRepository.findByDeletedAtBefore(date);
    }

    @Override
    public List<Comment> findByDateDeletedAfter(LocalDateTime date) {
        return commentRepository.findByDeletedAtAfter(date);
    }
    @Override
    public List<Comment> findByStatusId(Long statusId) {
        return commentRepository.findAllByStatusId(statusId);
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfter(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateModifiedBeforeAndDateDeletedAfter(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateModifiedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateDeletedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateDeletedAfterAndDateCreatedBefore(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateDeletedBeforeAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateDeletedAfterAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedAfterAndDateDeletedBeforeAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6, LocalDateTime date7) {
        return null;
    }

    @Override
    public List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBeforeAndDateCreatedAfterAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6, LocalDateTime date7, LocalDateTime date8) {
        return null;
    }
}
