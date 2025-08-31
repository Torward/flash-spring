package ru.lomov.flash.live.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "live_streams")
@Data
public class LiveStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "room_id", unique = true, nullable = false)
    private String roomId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "live_participants", joinColumns = @JoinColumn(name = "live_stream_id"))
    @Column(name = "user_id")
    private List<String> participants;
    
    @ElementCollection
    @CollectionTable(name = "live_chats", joinColumns = @JoinColumn(name = "live_stream_id"))
    @Column(name = "message")
    private List<String> chats;
    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "viewer_count")
    private Integer viewerCount = 0;
    
    @Column(name = "privacy")
    private String privacy = "public"; // public, private
    
    @Column(name = "stream_url")
    private String streamUrl;
    
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}
