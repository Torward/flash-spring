package ru.lomov.flash.chat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.chat.dto.ChatMessageDto;
import ru.lomov.flash.chat.services.ChatMessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ChatMessageDto> sendMessage(@RequestBody ChatMessageDto messageDto) {
        ChatMessageDto savedMessage = chatMessageService.sendMessage(messageDto);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/private/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessageDto>> getPrivateMessages(
            @PathVariable String senderId,
            @PathVariable String receiverId) {
        List<ChatMessageDto> messages = chatMessageService.getMessagesBetweenUsers(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ChatMessageDto>> getGroupMessages(@PathVariable String groupId) {
        List<ChatMessageDto> messages = chatMessageService.getGroupMessages(groupId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<ChatMessageDto>> getPartyMessages(@PathVariable String partyId) {
        List<ChatMessageDto> messages = chatMessageService.getPartyMessages(partyId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{messageId}/read")
    public ResponseEntity<ChatMessageDto> markAsRead(@PathVariable UUID messageId) {
        ChatMessageDto message = chatMessageService.markAsRead(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        chatMessageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
