
package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.PostRepository;
import ru.lomov.flashbackend.request.PostReplyRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;


    @Override
    public Post createPost(Post req, AppUser user) throws UserNotFoundException {
        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(req.getImage());
        post.setAppUser(user);
        post.setReply(false);
        post.setPost(true);
        post.setVideo(req.getVideo());
        return postRepository.save(post);
    }

    @Override
    public Post createReply(PostReplyRequest replyRequest, AppUser user) throws PostNotFoundException {
        Post replyFor = findById(replyRequest.getPostId());
        if (replyFor == null) {
            throw new PostNotFoundException("Пост с номером " + replyRequest.getPostId() + " не найден!");
        }
        Post post = new Post();
        post.setContent(replyRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(replyRequest.getImage());
        post.setAppUser(user);
        post.setReply(true);
        post.setPost(false);
        post.setReplyFor(replyFor);
        Post savedReply = postRepository.save(post);
        replyFor.getReplyPosts().add(savedReply);
        postRepository.save(replyFor);
        return savedReply;
    }


    @Override
    public Post repost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException {
        Post post = findById(postId);
        if (post == null) {
            throw new PostNotFoundException("Пост с номером " + postId + " не найден!");
        }
        if (post.getRepostAppUser().contains(user)) {
            post.getRepostAppUser().remove(user);
        } else {
            post.getRepostAppUser().add(user);
        }
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long postId) throws PostNotFoundException {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Пост с номером " + postId + " не найден!"));
    }

    @Override
    public Post removeFromRepost(Long postId, AppUser user) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow();
        post.getRepostAppUser().remove(user);
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAllByIsPostTrueOrderByCreatedAtAsc();
    }

    @Override
    public List<Post> getUserPosts(AppUser user) {
        return postRepository.findByRepostAppUserContainsOrAppUser_UserIdAndIsPostTrueOrderByCreatedAtDesc(user, user.getUserId());
    }

    @Override
    public List<Post> findByLikesContainsUser(AppUser user) {
        return postRepository.findByLikes_AppUser(user);
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Пост с номером " + postId + " не найден!"));
        if (!userId.equals(post.getAppUser().getUserId())) {
            throw new UserNotFoundException("Вы не можете удалять пост других пользователей");
        }
        postRepository.deleteById(post.getPostId());
    }

    @Override
    public List<Post> findByRepliesContainsUser(AppUser user) {
        return postRepository.findByAppUser_UserId(user.getUserId());
    }

    @Override
    public Post savePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с id: " + postId + " не найден"));
        AppUser user = userService.findUserById(userId);
        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
            post.setBookmarked(false);
        } else {
            user.getSavedPosts().add(post);
            post.setBookmarked(true);
        }
        userService.save(user);
        postRepository.save(post);
        return post;
    }
}