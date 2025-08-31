package ru.lomov.flash.saves.dto;

import lombok.Data;
import ru.lomov.flash.saves.entities.Save;

import java.time.LocalDateTime;

@Data
public class SaveResponse {
    private String id;
    private String postId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    // Firebase-compatible fields
    private String savedPostId;
    private String savedByUserId;
    private boolean isSaved;

    public static SaveResponse fromEntity(Save save) {
        SaveResponse response = new SaveResponse();
        response.setId(save.getId());
        response.setPostId(save.getPostId());
        response.setUserId(save.getUserId());
        response.setCreatedAt(save.getCreatedAt());
        response.setUpdatedAt(save.getUpdatedAt());
        response.setActive(save.isActive());
        response.setSavedPostId(save.getSavedPostId());
        response.setSavedByUserId(save.getSavedByUserId());
        response.setSaved(save.getIsSaved());
        return response;
    }
}
