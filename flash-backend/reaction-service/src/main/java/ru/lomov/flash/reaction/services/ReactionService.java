package ru.lomov.flash.reaction.services;

import ru.lomov.flash.reaction.dto.ReactionResponse;
import ru.lomov.flash.reaction.entities.Reaction;

import java.util.List;

public interface ReactionService {
    
    ReactionResponse addReaction(String postId, String userId, String reactionType);
    
    ReactionResponse updateReaction(String postId, String userId, String reactionType);
    
    ReactionResponse removeReaction(String postId, String userId);
    
    ReactionResponse getReaction(String postId, String userId);
    
    List<ReactionResponse> getPostReactions(String postId);
    
    List<ReactionResponse> getUserReactions(String userId);
    
    long getPostReactionsCount(String postId);
    
    long getUserReactionsCount(String userId);
    
    boolean hasUserReacted(String postId, String userId);
    
    String getUserReactionType(String postId, String userId);
    
    // Firebase-compatible methods
    ReactionResponse addReactionFirebase(String postId, String userId, String type);
    
    ReactionResponse updateReactionFirebase(String postId, String userId, String type);
    
    ReactionResponse removeReactionFirebase(String postId, String userId);
}
