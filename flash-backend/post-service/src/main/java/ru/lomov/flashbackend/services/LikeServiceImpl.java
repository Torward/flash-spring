package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.Like;
import ru.lomov.flashbackend.repositories.LikeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public Like createLike(Long postId, Long userId) {
        Like like = new Like(postId, userId);
        return likeRepository.save(like);
    }

    @Override
    public Like createLike(Long postId, Long userId, String reactionType) {
        Like like = new Like(postId, userId, reactionType);
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    @Override
    public void deleteLike(Long postId, Long userId) {
        Optional<Like> like = likeRepository.findByPostIdAndUserId(postId, userId);
        like.ifPresent(likeRepository::delete);
    }

    @Override
    public Optional<Like> getLikeById(Long likeId) {
        return likeRepository.findById(likeId);
    }

    @Override
    public Optional<Like> getLikeByPostAndUser(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId);
    }

    @Override
    public List<Like> getLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId);
    }

    @Override
    public List<Like> getLikesByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    @Override
    public long getLikeCountByPostId(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public long getLikeCountByUserId(Long userId) {
        return likeRepository.countByUserId(userId);
    }

    @Override
    public boolean hasUserLikedPost(Long postId, Long userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public List<Like> getLikesByPostIdAndReactionType(Long postId, String reactionType) {
        return likeRepository.findByPostIdAndReactionType(postId, reactionType);
    }

    @Override
    public long getLikeCountByPostIdAndReactionType(Long postId, String reactionType) {
        return likeRepository.countByPostIdAndReactionType(postId, reactionType);
    }
}
