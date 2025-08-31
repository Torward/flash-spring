package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column
    private String reactionType; // Для разных типов реакций (like, love, haha, wow, sad, angry)

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Конструктор для базового лайка
    public Like(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
        this.reactionType = "like";
    }

    // Конструктор для реакции с типом
    public Like(Long postId, Long userId, String reactionType) {
        this.postId = postId;
        this.userId = userId;
        this.reactionType = reactionType;
    }
}
