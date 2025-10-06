package ru.lomov.flash.podcast.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "podcasts")
@Data
public class Podcast {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();
    
    @Column(name = "room_id", unique = true, nullable = false)
    private String roomId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "podcast_participants", joinColumns = @JoinColumn(name = "podcast_id"))
    @Column(name = "user_id")
    private List<String> participants;
    
    @ElementCollection
    @CollectionTable(name = "podcast_chats", joinColumns = @JoinColumn(name = "podcast_id"))
    @Column(name = "message")
    private List<String> chats;
    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "listener_count")
    private Integer listenerCount = 0;
    
    @Column(name = "privacy")
    private String privacy = "public"; // public, private
    
    @Column(name = "audio_url")
    private String audioUrl;
    
    @Column(name = "cover_image_url")
    private String coverImageUrl;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "tags")
    private String tags;
}
