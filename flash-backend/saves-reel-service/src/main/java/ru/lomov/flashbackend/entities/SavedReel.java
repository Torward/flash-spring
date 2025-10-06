package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_reels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedReel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String saveId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String reelId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime savedAt;
}
