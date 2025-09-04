package ru.lomov.flash.chat.services;

import ru.lomov.flash.chat.dto.ChatMessageDto;
import ru.lomov.flash.chat.entities.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {

    ChatMessageDto sendMessage(ChatMessageDto messageDto);

    List<ChatMessageDto> getMessagesBetweenUsers(String senderId, String receiverId);

    List<ChatMessageDto> getGroupMessages(String groupId);

    List<ChatMessageDto> getPartyMessages(String partyId);

    ChatMessageDto markAsRead(UUID messageId);

    void deleteMessage(UUID messageId);
}
