package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.LikeRepository;
import ru.lomov.flashbackend.repositories.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public AppLike likePost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с номером " + postId + " не найден!"));
        AppLike isAppLikeExist = likeRepository.isLikeExist(user.getUserId(), postId);
        if (isAppLikeExist != null) {
            likeRepository.deleteById(isAppLikeExist.getId());
            post.getLikes().remove(isAppLikeExist);
            postRepository.save(post);
            return isAppLikeExist;
        }
        AppLike appLike = new AppLike();
        appLike.setPost(post);
        appLike.setAppUser(user);
        AppLike savedAppLike = likeRepository.save(appLike);
        post.getLikes().add(savedAppLike);
        postRepository.save(post);
        return savedAppLike;
    }

    @Override
    public List<AppLike> getAllLikes(Long postId) throws PostNotFoundException {
        return likeRepository.findAllByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Post> getLikedPostsByUser(AppUser user) {
        List<AppLike> likes = likeRepository.findByAppUser(user);
        return likes.stream()
                .map(AppLike::getPost)
                .collect(Collectors.toList());
    }
}
