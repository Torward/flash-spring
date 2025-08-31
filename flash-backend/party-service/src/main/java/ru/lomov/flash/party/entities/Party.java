package ru.lomov.flash.party.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "party")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ElementCollection
    @CollectionTable(name = "party_users", joinColumns = @JoinColumn(name = "party_id"))
    @Column(name = "user_id")
    private List<String> users;

    @ElementCollection
    @CollectionTable(name = "party_chats", joinColumns = @JoinColumn(name = "party_id"))
    @Column(name = "message")
    private List<String> chats;

    @Column
    private String video;

    @Column
    private String privacy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
