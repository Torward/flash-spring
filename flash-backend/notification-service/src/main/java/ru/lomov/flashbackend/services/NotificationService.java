package ru.lomov.flashbackend.services;

import java.util.List;

public interface NotificationService {
    
    /**
     * Получить уведомления пользователя
     * @param userId ID пользователя
     * @return список уведомлений
     */
    List<String> getUserNotifications(Long userId);
    
    /**
     * Отметить уведомление как прочитанное
     * @param notificationId ID уведомления
     * @param userId ID пользователя
     */
    void markAsRead(Long notificationId, Long userId);
    
    /**
     * Удалить уведомление
     * @param notificationId ID уведомления
     * @param userId ID пользователя
     */
    void deleteNotification(Long notificationId, Long userId);
}
