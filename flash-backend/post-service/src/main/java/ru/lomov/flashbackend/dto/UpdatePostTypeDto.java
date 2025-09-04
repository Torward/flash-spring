package ru.lomov.flashbackend.dto;

import lombok.Data;

@Data
public class UpdatePostTypeDto {
    private String type; // "text", "image", "video", "bg", "meme"
    private String vine; // Video URL for vine-type posts
    private String meme; // Image URL for meme-type posts
}
