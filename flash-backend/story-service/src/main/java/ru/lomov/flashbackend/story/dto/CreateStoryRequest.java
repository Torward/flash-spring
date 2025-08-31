package ru.lomov.flashbackend.story.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateStoryRequest {
    private String type; // image, video, gif, text, poll, quiz, question, countdown, emoji_slider
    private String image; // URL изображения
    private String video; // URL видео
    private String text;
    private String location;
    private String privacy = "public"; // public, private, friends
    private String background;
    private String font;
    private String color;
    
    // Additional Firebase fields
    private String hashtags;
    private String mentions;
    private String link;
    private Integer duration; // Длительность в секундах для видео
    private String aspectRatio; // Соотношение сторон
    private String filter; // Фильтр для изображения
    private String music; // Музыка для видео
    private String productTag; // Тег товара
    private String locationId; // ID локации
    private String pollQuestion; // Вопрос для опроса
    private String pollOptions; // Варианты ответов для опроса
    private String quizQuestion; // Вопрос для викторины
    private String quizAnswer; // Ответ для викторины
    private String emojiSlider; // Эмодзи для слайдера
    private String question; // Вопрос для формата "задай вопрос"
    private LocalDateTime countdownEnd; // Конец отсчета времени
}
