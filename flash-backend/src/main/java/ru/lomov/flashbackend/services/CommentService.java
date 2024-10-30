package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.CommentNotFoundException;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.request.CommentReplyRequest;
import ru.lomov.flashbackend.request.PostReplyRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    Comment findById(Long id);
    Comment createComment(Comment req, AppUser user) throws UserNotFoundException;
    Comment createReply(CommentReplyRequest replyRequest, AppUser user) throws CommentNotFoundException;
    List<Comment> findByPostIdAndDeletedAtIsNull(Long postId);
    List<Comment> findByPostIdAndDeletedAtBetween(Long postId, LocalDateTime startDate, LocalDateTime endDate);
    List<Comment> findByStatusId(Long statusId);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthor(AppUser author);
    List<Comment> findByContentContains(String content);
    List<Comment> findByLikesCountGreaterThan(int likesCount);
    List<Comment> findByCommentsCountGreaterThan(int commentsCount);
    List<Comment> findByDateCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Comment> findByDateModifiedBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Comment> findByDateDeletedBetween(LocalDateTime startDate, LocalDateTime endDate);
    Comment save(Comment comment);
    Comment update(Comment comment);
    void delete(Comment comment);
    List<Comment> findByDateCreatedBefore(LocalDateTime date);
    List<Comment> findByDateCreatedAfter(LocalDateTime date);
    List<Comment> findByDateModifiedBefore(LocalDateTime date);
    List<Comment> findByDateModifiedAfter(LocalDateTime date);
    List<Comment> findByDateDeletedBefore(LocalDateTime date);
    List<Comment> findByDateDeletedAfter(LocalDateTime date);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfter(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateCreatedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateModifiedBeforeAndDateDeletedAfter(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateModifiedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateDeletedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateDeletedAfterAndDateCreatedBefore(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateDeletedBeforeAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateDeletedAfterAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3);
    List<Comment> findByDateCreatedAfterAndDateDeletedBeforeAndDateModifiedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBefore(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBeforeAndDateCreatedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6, LocalDateTime date7);
    List<Comment> findByDateCreatedBeforeAndDateDeletedAfterAndDateModifiedBeforeAndDateCreatedAfterAndDateModifiedAfterAndDateDeletedBeforeAndDateCreatedAfterAndDateModifiedAfter(LocalDateTime date1, LocalDateTime date2, LocalDateTime date3, LocalDateTime date4, LocalDateTime date5, LocalDateTime date6, LocalDateTime date7, LocalDateTime date8);
}
