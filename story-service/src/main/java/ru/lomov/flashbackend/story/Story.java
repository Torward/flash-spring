package ru.lomov.flashbackend.story;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
@Data
public class Story {
    
    @Id
    @UuidGenerator
    @Column(name = "story_id", nullable = false, unique = true, updatable = false)
    private String storyId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String type; // image, video, gif, text
    
    private String image; // URL изображения
    private String video; // URL видео
    
    @Column(name = "time_start", nullable = false)
    private LocalDateTime timeStart;
    
    @Column(name = "time_end", nullable = false)
    private LocalDateTime timeEnd;
    
    @Column(nullable = false)
    private Integer views = 0;
    
    // Firebase-compatible fields
    private String text;
    private String location;
    private String privacy = "public"; // public, private, friends
    private String background;
    private String font;
    private String color;
    
    // Additional Firebase fields
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;
    
    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;
    
    @Column(name = "share_count", nullable = false)
    private Integer shareCount = 0;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
    
    @Column(name = "is_highlight", nullable = false)
    private Boolean isHighlight = false;
    
    @Column(name = "duration")
    private Integer duration; // Длительность в секундах для видео
    
    @Column(name = "aspect_ratio")
    private String aspectRatio; // Соотношение сторон
    
    @Column(name = "filter")
    private String filter; // Фильтр для изображения
    
    @Column(name = "music")
    private String music; // Музыка для видео
    
    @Column(name = "mentions")
    private String mentions; // Упомянутые пользователи
    
    @Column(name = "hashtags")
    private String hashtags; // Хэштеги
    
    @Column(name = "link")
    private String link; // Ссылка в истории
    
    @Column(name = "product_tag")
    private String productTag; // Тег товара
    
    @Column(name = "location_id")
    private String locationId; // ID локации
    
    @Column(name = "poll_question")
    private String pollQuestion; // Вопрос для опроса
    
    @Column(name = "poll_options")
    private String pollOptions; // Варианты ответов для опроса
    
    @Column(name = "quiz_question")
    private String quizQuestion; // Вопрос для викторины
    
    @Column(name = "quiz_answer")
    private String quizAnswer; // Ответ для викторины
    
    @Column(name = "emoji_slider")
    private String emojiSlider; // Эмодзи для слайдера
    
    @Column(name = "question")
    private String question; // Вопрос для формата "задай вопрос"
    
    @Column(name = "countdown_end")
    private LocalDateTime countdownEnd; // Конец отсчета времени
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        timeStart = LocalDateTime.now();
        timeEnd = timeStart.plusHours(24); // Истории живут 24 часа
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Firebase-compatible getter for ID
    public String getId() {
        return storyId;
    }
    
    public void setId(String storyId) {
        this.storyId = storyId;
    }
}
