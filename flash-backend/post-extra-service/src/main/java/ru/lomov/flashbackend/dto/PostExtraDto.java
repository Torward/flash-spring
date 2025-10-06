package ru.lomov.flashbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostExtraDto {
    private String postId;
    private String extraData;
    private String metadata;
    private LocalDateTime updatedAt;
}
