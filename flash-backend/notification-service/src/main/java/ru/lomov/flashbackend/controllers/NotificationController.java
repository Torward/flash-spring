package ru.lomov.flashbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.Notification;
import ru.lomov.flashbackend.services.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API для управления уведомлениями")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить уведомления пользователя")
    public ResponseEntity<List<String>> getUserNotifications(@PathVariable Long userId) {
        List<String> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/{notificationId}/read/user/{userId}")
    @Operation(summary = "Отметить уведомление как прочитанное")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId, @PathVariable Long userId) {
        notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok("Уведомление отмечено как прочитанное");
    }

    @DeleteMapping("/{notificationId}/user/{userId}")
    @Operation(summary = "Удалить уведомление")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId, @PathVariable Long userId) {
        notificationService.deleteNotification(notificationId, userId);
        return ResponseEntity.ok("Уведомление удалено");
    }

    @GetMapping("/health")
    @Operation(summary = "Проверка здоровья сервиса")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Notification Service is healthy");
    }
}
