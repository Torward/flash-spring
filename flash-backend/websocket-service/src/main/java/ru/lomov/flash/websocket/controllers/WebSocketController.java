package ru.lomov.flash.websocket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    // Store active sessions for user tracking
    private final Map<String, String> userSessions = new ConcurrentHashMap<>();

    // Chat message handling
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add timestamp and sender info
        chatMessage.setTimestamp(System.currentTimeMillis());

        Principal principal = headerAccessor.getUser();
        if (principal != null) {
            chatMessage.setSender(principal.getName());
        }

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add user to session
        Principal principal = headerAccessor.getUser();
        if (principal != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            userSessions.put(chatMessage.getSender(), headerAccessor.getSessionId());
        }

        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setTimestamp(System.currentTimeMillis());
        return chatMessage;
    }

    // Private messaging
    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload PrivateMessage privateMessage) {
        messagingTemplate.convertAndSendToUser(
            privateMessage.getRecipient(),
            "/queue/messages",
            privateMessage
        );
    }

    // Notification broadcasting
    @MessageMapping("/notification.send")
    @SendTo("/topic/notifications")
    public NotificationMessage sendNotification(@Payload NotificationMessage notification) {
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    // Live streaming events
    @MessageMapping("/live.update")
    @SendTo("/topic/live/{streamId}")
    public LiveUpdateMessage updateLiveStream(
            @DestinationVariable String streamId,
            @Payload LiveUpdateMessage update) {
        update.setTimestamp(System.currentTimeMillis());
        return update;
    }

    // User status updates
    @MessageMapping("/user.status")
    @SendTo("/topic/user/{userId}/status")
    public UserStatusMessage updateUserStatus(
            @DestinationVariable String userId,
            @Payload UserStatusMessage status) {
        status.setTimestamp(System.currentTimeMillis());
        return status;
    }

    // Typing indicators
    @MessageMapping("/chat.typing")
    @SendTo("/topic/chat/{chatId}/typing")
    public TypingIndicatorMessage typingIndicator(
            @DestinationVariable String chatId,
            @Payload TypingIndicatorMessage indicator) {
        return indicator;
    }

    // Message delivery receipts
    @MessageMapping("/message.delivered")
    public void messageDelivered(@Payload DeliveryReceiptMessage receipt) {
        messagingTemplate.convertAndSendToUser(
            receipt.getSenderId(),
            "/queue/delivered",
            receipt
        );
    }

    // Message read receipts
    @MessageMapping("/message.read")
    public void messageRead(@Payload ReadReceiptMessage receipt) {
        messagingTemplate.convertAndSendToUser(
            receipt.getSenderId(),
            "/queue/read",
            receipt
        );
    }
}

// Message DTOs
class ChatMessage {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    private MessageType type;
    private String content;
    private String sender;
    private Long timestamp;

    // Getters and setters
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class PrivateMessage {
    private String content;
    private String sender;
    private String recipient;
    private Long timestamp;

    // Getters and setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class NotificationMessage {
    private String title;
    private String message;
    private String type;
    private String userId;
    private Long timestamp;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class LiveUpdateMessage {
    private String streamId;
    private String type; // viewer_count, status, etc.
    private Object data;
    private Long timestamp;

    // Getters and setters
    public String getStreamId() { return streamId; }
    public void setStreamId(String streamId) { this.streamId = streamId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class UserStatusMessage {
    private String userId;
    private String status; // online, offline, away
    private Long timestamp;

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class TypingIndicatorMessage {
    private String chatId;
    private String userId;
    private boolean isTyping;

    // Getters and setters
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public boolean isTyping() { return isTyping; }
    public void setTyping(boolean typing) { isTyping = typing; }
}

class DeliveryReceiptMessage {
    private String messageId;
    private String senderId;
    private String recipientId;
    private Long timestamp;

    // Getters and setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

class ReadReceiptMessage {
    private String messageId;
    private String senderId;
    private String readerId;
    private Long timestamp;

    // Getters and setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getReaderId() { return readerId; }
    public void setReaderId(String readerId) { this.readerId = readerId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}
