# Tokens Service

Сервис для управления FCM токенами пользователей. Позволяет сохранять, обновлять и удалять токены для отправки push-уведомлений.

## API Endpoints

### Сохранить/обновить токен
- **POST** `/tokens/{userId}`
- Тело запроса:
```json
{
  "token": "fcm_token_string",
  "deviceId": "device_identifier"
}
```

### Получить токен пользователя
- **GET** `/tokens/{userId}`

### Обновить токен
- **PUT** `/tokens/{userId}`
- Тело запроса аналогично POST

### Удалить токен
- **DELETE** `/tokens/{userId}?token={token_value}` (удалить конкретный токен)
- **DELETE** `/tokens/{userId}` (удалить все токены пользователя)

### Получить все токены пользователя
- **GET** `/tokens/{userId}/all`

### Проверить существование токена
- **GET** `/tokens/check?userId={userId}&token={token_value}`

## Запуск

1. Собрать проект:
```bash
mvn clean package
```

2. Запустить через Docker:
```bash
docker build -t tokens-service .
docker run -p 8087:8087 tokens-service
```

3. Или напрямую через Maven:
```bash
mvn spring-boot:run
```

## Конфигурация

- Порт: 8087
- Имя сервиса: tokens-service
- База данных: H2 (in-memory для разработки)
- Service Discovery: Eureka Client
