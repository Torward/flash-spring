package ru.lomov.flash.likes.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Data
public class Like {
    @Id
    @UuidGenerator
    @Column(name = "like_id", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "post_id", nullable = false)
    private String postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // Firebase-compatible fields
    @Column(name = "liked_post_id")
    private String likedPostId;

    @Column(name = "liked_by_user_id")
    private String likedByUserId;

    @Column(name = "like_count")
    private int likeCount = 0;

    @Column(name = "is_liked")
    private boolean isLiked = false;

    // Firebase-compatible getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikedPostId() {
        return likedPostId != null ? likedPostId : postId;
    }

    public String getLikedByUserId() {
        return likedByUserId != null ? likedByUserId : userId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
