package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reel_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReelView {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String viewId;

    @Column(nullable = false)
    private String reelId;

    @Column(nullable = false)
    private String userId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime viewedAt;

    private Integer viewDuration; // in seconds

    private String deviceInfo;
}
