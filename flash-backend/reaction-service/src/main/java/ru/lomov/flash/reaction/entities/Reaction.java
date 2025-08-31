package ru.lomov.flash.reaction.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "reactions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Data
public class Reaction {
    
    public enum ReactionType {
        LIKE, LOVE, LAUGH, WOW, SAD, ANGRY
    }

    @Id
    @UuidGenerator
    @Column(name = "reaction_id", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "post_id", nullable = false)
    private String postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // Firebase-compatible fields
    @Column(name = "reacted_post_id")
    private String reactedPostId;

    @Column(name = "reacted_by_user_id")
    private String reactedByUserId;

    // Firebase-compatible getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReactedPostId() {
        return reactedPostId != null ? reactedPostId : postId;
    }

    public String getReactedByUserId() {
        return reactedByUserId != null ? reactedByUserId : userId;
    }

    public String getType() {
        return reactionType != null ? reactionType.name().toLowerCase() : "like";
    }

    public void setType(String type) {
        try {
            this.reactionType = ReactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.reactionType = ReactionType.LIKE;
        }
    }
}
