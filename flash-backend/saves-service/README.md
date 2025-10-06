# Saves Service

Микросервис для управления сохраненными постами.

## Эндпоинты

### Основные эндпоинты

- `POST /saves/{postId}/{userId}` - Сохранить пост
- `DELETE /saves/{postId}/{userId}` - Удалить пост из сохраненных
- `GET /saves/{postId}/{userId}` - Получить информацию о сохранении
- `GET /saves/user/{userId}` - Получить все сохраненные посты пользователя
- `GET /saves/post/{postId}` - Получить всех пользователей, сохранивших пост
- `GET /saves/user/{userId}/count` - Получить количество сохраненных постов пользователя
- `GET /saves/post/{postId}/count` - Получить количество сохранений поста
- `GET /saves/{postId}/{userId}/isSaved` - Проверить, сохранен ли пост пользователем

## Архитектура

Сервис построен на микросервисной архитектуре с использованием Spring Boot:
- Использует String ID (UUID)
- Поддерживает все необходимые поля и методы
- Интегрируется с PostgreSQL базой данных

## Запуск

### Локальный запуск

```bash
cd saves-service
mvn spring-boot:run
```

### Docker

```bash
docker build -t saves-service .
docker run -p 8084:8084 saves-service
```

## Конфигурация

Порт: 8084
База данных: PostgreSQL
Service Discovery: Eureka
