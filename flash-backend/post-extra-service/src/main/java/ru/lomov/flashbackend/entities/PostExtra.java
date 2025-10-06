package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_extra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostExtra {
    @Id
    private String postId; // Same as Post.postId

    @Column(columnDefinition = "TEXT")
    private String extraData; // JSON string

    @Column(columnDefinition = "TEXT")
    private String metadata; // JSON string

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
