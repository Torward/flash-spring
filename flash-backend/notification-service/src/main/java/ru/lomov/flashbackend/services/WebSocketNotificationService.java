package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.Notification;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(Long userId, Notification notification) {
        String destination = "/topic/notifications/" + userId;
        messagingTemplate.convertAndSend(destination, notification);
    }

    public void sendNotificationToAllUsers(Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    public void sendNotificationCount(Long userId, Long count) {
        String destination = "/topic/notification-count/" + userId;
        messagingTemplate.convertAndSend(destination, count);
    }
}
