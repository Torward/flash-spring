package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reel_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReelLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String likeId;

    @Column(nullable = false)
    private String reelId;

    @Column(nullable = false)
    private String userId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime likedAt;
}
