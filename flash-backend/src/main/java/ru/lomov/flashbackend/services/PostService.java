package ru.lomov.flashbackend.services;


import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.request.PostReplyRequest;

import java.util.List;

public interface PostService {

    Post createPost(Post req, AppUser user) throws UserNotFoundException;
    Post repost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException;
    Post findById(Long postId) throws PostNotFoundException;
//    void share(Long postId, AppUser user) throws PostNotFoundException, UserNotFoundException;
//    void unShare(Long postId, AppUser user) throws PostNotFoundException, UserNotFoundException;
    Post removeFromRepost(Long postId, AppUser user) throws PostNotFoundException, UserNotFoundException;
    Post createReply(PostReplyRequest replyRequest, AppUser user) throws PostNotFoundException;
    List<Post> findAllPosts();
    List<Post> getUserPosts(AppUser user);
    List<Post> findByLikesContainsUser(AppUser user);
    void deletePostById(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException;
//    Post updatePost(Post req, AppUser user);
    List<Post> findByRepliesContainsUser(AppUser user);

    Post savePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException;
}