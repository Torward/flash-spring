package ru.lomov.flash.saves.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "saves", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Data
public class Save {
    @Id
    @UuidGenerator
    @Column(name = "save_id", nullable = false, unique = true, updatable = false)
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
    @Column(name = "saved_post_id")
    private String savedPostId;

    @Column(name = "saved_by_user_id")
    private String savedByUserId;

    @Column(name = "is_saved")
    private boolean isSaved = false;

    // Firebase-compatible getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSavedPostId() {
        return savedPostId != null ? savedPostId : postId;
    }

    public String getSavedByUserId() {
        return savedByUserId != null ? savedByUserId : userId;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
}
