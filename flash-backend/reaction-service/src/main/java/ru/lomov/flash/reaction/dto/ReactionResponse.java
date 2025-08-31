package ru.lomov.flash.reaction.dto;

import lombok.Data;
import ru.lomov.flash.reaction.entities.Reaction;

import java.time.LocalDateTime;

@Data
public class ReactionResponse {
    private String id;
    private String postId;
    private String userId;
    private String reactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    // Firebase-compatible fields
    private String reactedPostId;
    private String reactedByUserId;
    private String type;

    public static ReactionResponse fromEntity(Reaction reaction) {
        ReactionResponse response = new ReactionResponse();
        response.setId(reaction.getId());
        response.setPostId(reaction.getPostId());
        response.setUserId(reaction.getUserId());
        response.setReactionType(reaction.getReactionType().name());
        response.setCreatedAt(reaction.getCreatedAt());
        response.setUpdatedAt(reaction.getUpdatedAt());
        response.setActive(reaction.isActive());
        response.setReactedPostId(reaction.getReactedPostId());
        response.setReactedByUserId(reaction.getReactedByUserId());
        response.setType(reaction.getType());
        return response;
    }
}
