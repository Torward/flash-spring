package ru.lomov.flash.chat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.chat.dto.ChatMessageDto;
import ru.lomov.flash.chat.entities.ChatMessage;
import ru.lomov.flash.chat.repositories.ChatMessageRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageDto sendMessage(ChatMessageDto messageDto) {
        ChatMessage message = convertToEntity(messageDto);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        return convertToDto(savedMessage);
    }

    @Override
    public List<ChatMessageDto> getMessagesBetweenUsers(String senderId, String receiverId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdAndReceiverIdOrderByCreatedAtDesc(senderId, receiverId);
        return messages.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDto> getGroupMessages(String groupId) {
        List<ChatMessage> messages = chatMessageRepository.findByGroupIdOrderByCreatedAtDesc(groupId);
        return messages.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDto> getPartyMessages(String partyId) {
        List<ChatMessage> messages = chatMessageRepository.findByPartyIdOrderByCreatedAtDesc(partyId);
        return messages.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ChatMessageDto markAsRead(UUID messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId).orElseThrow();
        message.setRead(true);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        return convertToDto(savedMessage);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        chatMessageRepository.deleteById(messageId);
    }

    private ChatMessage convertToEntity(ChatMessageDto dto) {
        ChatMessage message = new ChatMessage();
        message.setId(dto.getId());
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setGroupId(dto.getGroupId());
        message.setPartyId(dto.getPartyId());
        message.setContent(dto.getContent());
        message.setMessageType(dto.getMessageType());
        message.setMediaUrl(dto.getMediaUrl());
        message.setRead(dto.isRead());
        message.setCreatedAt(dto.getCreatedAt());
        message.setUpdatedAt(dto.getUpdatedAt());
        return message;
    }

    private ChatMessageDto convertToDto(ChatMessage message) {
        return new ChatMessageDto(
                message.getId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getGroupId(),
                message.getPartyId(),
                message.getContent(),
                message.getMessageType(),
                message.getMediaUrl(),
                message.isRead(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
