package ru.lomov.flash.reaction.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.reaction.dto.ReactionResponse;
import ru.lomov.flash.reaction.entities.Reaction;
import ru.lomov.flash.reaction.repositories.ReactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;

    @Override
    public ReactionResponse addReaction(String postId, String userId, String reactionType) {
        Optional<Reaction> existingReaction = reactionRepository.findByPostIdAndUserId(postId, userId);

        Reaction reaction;
        if (existingReaction.isPresent()) {
            reaction = existingReaction.get();
            reaction.setIsActive(true);
            reaction.setType(reactionType);
            reaction.setUpdatedAt(LocalDateTime.now());
        } else {
            reaction = new Reaction();
            reaction.setPostId(postId);
            reaction.setUserId(userId);
            reaction.setReactedPostId(postId);
            reaction.setReactedByUserId(userId);
            reaction.setType(reactionType);
            reaction.setIsActive(true);
        }

        Reaction savedReaction = reactionRepository.save(reaction);
        return ReactionResponse.fromEntity(savedReaction);
    }

    @Override
    public ReactionResponse updateReaction(String postId, String userId, String reactionType) {
        Optional<Reaction> existingReaction = reactionRepository.findByPostIdAndUserId(postId, userId);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            reaction.setType(reactionType);
            reaction.setUpdatedAt(LocalDateTime.now());

            Reaction savedReaction = reactionRepository.save(reaction);
            return ReactionResponse.fromEntity(savedReaction);
        }

        return null;
    }

    @Override
    public ReactionResponse removeReaction(String postId, String userId) {
        Optional<Reaction> existingReaction = reactionRepository.findByPostIdAndUserId(postId, userId);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            reaction.setIsActive(false);
            reaction.setUpdatedAt(LocalDateTime.now());

            Reaction savedReaction = reactionRepository.save(reaction);
            return ReactionResponse.fromEntity(savedReaction);
        }

        return null;
    }

    @Override
    public ReactionResponse getReaction(String postId, String userId) {
        Optional<Reaction> reaction = reactionRepository.findByPostIdAndUserId(postId, userId);
        return reaction.map(ReactionResponse::fromEntity).orElse(null);
    }

    @Override
    public List<ReactionResponse> getPostReactions(String postId) {
        List<Reaction> reactions = reactionRepository.findActiveReactionsByPostId(postId);
        return reactions.stream()
                .map(ReactionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReactionResponse> getUserReactions(String userId) {
        List<Reaction> reactions = reactionRepository.findActiveReactionsByUserId(userId);
        return reactions.stream()
                .map(ReactionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long getPostReactionsCount(String postId) {
        return reactionRepository.countActiveReactionsByPostId(postId);
    }

    @Override
    public long getUserReactionsCount(String userId) {
        return reactionRepository.countActiveReactionsByUserId(userId);
    }

    @Override
    public boolean hasUserReacted(String postId, String userId) {
        return reactionRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public String getUserReactionType(String postId, String userId) {
        Optional<Reaction.ReactionType> reactionType = reactionRepository.findActiveReactionType(postId, userId);
        return reactionType.map(Enum::name).orElse(null);
    }

    @Override
    public ReactionResponse addReactionFirebase(String postId, String userId, String type) {
        return addReaction(postId, userId, type);
    }

    @Override
    public ReactionResponse updateReactionFirebase(String postId, String userId, String type) {
        return updateReaction(postId, userId, type);
    }

    @Override
    public ReactionResponse removeReactionFirebase(String postId, String userId) {
        return removeReaction(postId, userId);
    }
}
