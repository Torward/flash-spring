# Live Service

Микросервис для управления прямыми трансляциями в соответствии с Firebase архитектурой.

## Функциональность

- Создание и управление прямыми трансляциями
- Отслеживание участников трансляций
- Чат в реальном времени для трансляций
- Подсчет зрителей
- Интеграция с Eureka Service Discovery

## Эндпоинты

- `POST /live` - Начать трансляцию
- `GET /live/{roomId}` - Получить данные трансляции
- `DELETE /live/{roomId}` - Завершить трансляцию

## Структура данных

```json
{
  "roomId": "string",
  "userId": "string",
  "title": "string",
  "description": "string",
  "participants": ["string"],
  "chats": ["string"],
  "startTime": "datetime",
  "endTime": "datetime",
  "isActive": "boolean",
  "viewerCount": "integer",
  "privacy": "string",
  "streamUrl": "string",
  "thumbnailUrl": "string"
}
```

## Запуск

```bash
cd live-service
mvn clean install
mvn spring-boot:run
```

## Docker

```bash
docker build -t live-service .
docker run -p 8083:8083 live-service
