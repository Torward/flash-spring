# Likes Service

Микросервис для управления лайками постов.

## Эндпоинты

### Основные эндпоинты

- `POST /likes/{postId}/{userId}` - Поставить лайк посту
- `DELETE /likes/{postId}/{userId}` - Убрать лайк с поста
- `GET /likes/{postId}/count` - Получить количество лайков поста
- `GET /likes/{postId}/users` - Получить список пользователей, лайкнувших пост
- `GET /likes/user/{userId}/posts` - Получить список постов, лайкнутых пользователем
- `GET /likes/{postId}/{userId}/isLiked` - Проверить, лайкнул ли пользователь пост
- `GET /likes/user/{userId}/count` - Получить количество лайков пользователя

## Firebase-совместимость

Сервис полностью совместим с Firebase Realtime Database:
- Использует String ID (UUID)
- Поддерживает все необходимые поля и методы
- Соответствует структуре данных Firebase

## Запуск

### Локальный запуск

```bash
cd likes-service
mvn spring-boot:run
```

### Docker

```bash
docker build -t likes-service .
docker run -p 8083:8083 likes-service
```

## Конфигурация

Порт: 8083
База данных: PostgreSQL
Service Discovery: Eureka
