package ru.lomov.flash.likes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.likes.dto.LikeResponse;
import ru.lomov.flash.likes.entities.Like;
import ru.lomov.flash.likes.repositories.LikeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public LikeResponse likePost(String postId, String userId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
        
        Like like;
        if (existingLike.isPresent()) {
            like = existingLike.get();
            like.setIsActive(true);
            like.setIsLiked(true);
            like.setUpdatedAt(LocalDateTime.now());
        } else {
            like = new Like();
            like.setPostId(postId);
            like.setUserId(userId);
            like.setLikedPostId(postId);
            like.setLikedByUserId(userId);
            like.setIsActive(true);
            like.setIsLiked(true);
        }
        
        Like savedLike = likeRepository.save(like);
        return mapToResponse(savedLike);
    }

    @Override
    public LikeResponse unlikePost(String postId, String userId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
        
        if (existingLike.isPresent()) {
            Like like = existingLike.get();
            like.setIsActive(false);
            like.setIsLiked(false);
            like.setUpdatedAt(LocalDateTime.now());
            
            Like savedLike = likeRepository.save(like);
            return mapToResponse(savedLike);
        }
        
        // Return empty response if not liked
        LikeResponse response = new LikeResponse();
        response.setPostId(postId);
        response.setUserId(userId);
        return response;
    }

    @Override
    public long getLikesCount(String postId) {
        return likeRepository.countActiveLikesByPostId(postId);
    }

    @Override
    public List<LikeResponse> getPostLikes(String postId) {
        List<Like> likes = likeRepository.findActiveLikesByPostId(postId);
        return likes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeResponse> getUserLikes(String userId) {
        List<Like> likes = likeRepository.findActiveLikesByUserId(userId);
        return likes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPostLiked(String postId, String userId) {
        return likeRepository.isPostLiked(postId, userId);
    }

    @Override
    public long getUserLikesCount(String userId) {
        return likeRepository.countActiveLikesByUserId(userId);
    }

    private LikeResponse mapToResponse(Like like) {
        LikeResponse response = new LikeResponse();
        response.setId(like.getId());
        response.setPostId(like.getPostId());
        response.setUserId(like.getUserId());
        response.setCreatedAt(like.getCreatedAt());
        response.setUpdatedAt(like.getUpdatedAt());
        return response;
    }
}
