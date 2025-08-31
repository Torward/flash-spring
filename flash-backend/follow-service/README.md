# Follow Service

Микросервис для управления подписками и подписчиками пользователей.

## Эндпоинты

### Основные эндпоинты

- `POST /follow/{userId}/following/{targetUserId}` - Подписаться на пользователя
- `DELETE /follow/{userId}/following/{targetUserId}` - Отписаться от пользователя
- `GET /follow/{userId}/following` - Получить список подписок
- `GET /follow/{userId}/followers` - Получить список подписчиков
- `GET /follow/{userId}/following/count` - Получить количество подписок
- `GET /follow/{userId}/followers/count` - Получить количество подписчиков
- `GET /follow/{userId}/isFollowing/{targetUserId}` - Проверить, подписан ли пользователь
- `GET /follow/{userId}/mutual/{targetUserId}` - Получить общих подписчиков

## Firebase-совместимость

Сервис полностью совместим с Firebase Realtime Database:
- Использует String ID (UUID)
- Поддерживает все необходимые поля и методы
- Соответствует структуре данных Firebase

## Запуск

### Локальный запуск

```bash
cd follow-service
mvn spring-boot:run
```

### Docker

```bash
docker build -t follow-service .
docker run -p 8082:8082 follow-service
```

## Конфигурация

Порт: 8082
База данных: PostgreSQL
Service Discovery: Eureka
