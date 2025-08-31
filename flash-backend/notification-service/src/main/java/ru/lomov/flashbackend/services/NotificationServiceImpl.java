package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.entities.Notification;
import ru.lomov.flashbackend.repositories.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final WebSocketNotificationService webSocketNotificationService;

    @Override
    public List<String> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, null)
                .stream()
                .map(notification -> notification.getMessage())
                .toList();
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        notificationRepository.markAsRead(userId, notificationId);
        
        // Send WebSocket notification about read status
        Long unreadCount = notificationRepository.countByUserIdAndIsReadFalse(userId);
        webSocketNotificationService.sendNotificationCount(userId, unreadCount);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId, Long userId) {
        notificationRepository.deleteByUserIdAndId(userId, notificationId);
        
        // Send WebSocket notification about updated count
        Long unreadCount = notificationRepository.countByUserIdAndIsReadFalse(userId);
        webSocketNotificationService.sendNotificationCount(userId, unreadCount);
    }
}
