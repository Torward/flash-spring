package ru.lomov.flash.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private UUID id;
    private String senderId;
    private String receiverId;
    private String groupId;
    private String partyId;
    private String content;
    private String messageType;
    private String mediaUrl;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
