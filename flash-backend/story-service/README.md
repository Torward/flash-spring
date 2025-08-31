# Story Service

Микросервис для управления историями (stories) в социальной сети Flash. Поддерживает создание, просмотр, управление и автоматическое удаление историй через 24 часа.

## Особенности

- ✅ Firebase-совместимая структура данных
- ✅ Автоматическое удаление историй через 24 часа
- ✅ Поддержка различных типов контента (изображения, видео, текст)
- ✅ Отслеживание просмотров
- ✅ Настройки приватности
- ✅ Интеграция с Eureka Service Discovery

## Технологии

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (для разработки)
- Eureka Client
- Lombok
- Maven

## Структура данных

### Story Entity
```java
public class Story {
    private Long id;
    private Long userId;
    private String type;        // "image", "video", "gif", "text"
    private String image;       // URL изображения
    private String video;       // URL видео
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd; // Автоматически = timeStart + 24 часа
    private Integer views = 0;
    private String text;
    private String location;
    private String privacy;     // "public", "private", "friends"
    private String background;
    private String font;
    private String color;
}
```

## API Endpoints

### Основные эндпоинты

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| POST | `/story/{userId}` | Создать новую историю |
| GET | `/story/{userId}` | Получить все истории пользователя |
| GET | `/story/{userId}/active` | Получить активные истории пользователя |
| GET | `/story/active` | Получить активные истории по списку пользователей |
| GET | `/story/popular` | Получить популярные истории (по просмотрам) |
| DELETE | `/story/{userId}/{storyId}` | Удалить историю |
| POST | `/story/{storyId}/view` | Увеличить счетчик просмотров |
| GET | `/story/{userId}/count` | Получить количество историй пользователя |
| DELETE | `/story/cleanup/expired` | Удалить истекшие истории (админ) |
| GET | `/story/health` | Проверка здоровья сервиса |

### Примеры запросов

**Создание истории:**
```bash
curl -X POST http://localhost:8083/story/1 \
  -H "Content-Type: application/json" \
  -d '{
    "type": "image",
    "image": "https://example.com/image.jpg",
    "text": "Моя первая история!",
    "privacy": "public",
    "background": "#ffffff",
    "font": "Arial",
    "color": "#000000"
  }'
```

**Получение активных историй пользователя:**
```bash
curl http://localhost:8083/story/1/active
```

**Увеличение просмотров:**
```bash
curl -X POST http://localhost:8083/story/123/view
```

## Запуск

### Локальный запуск (разработка)
```bash
cd story-service
./mvnw spring-boot:run
```

### Сборка и запуск с Docker
```bash
# Сборка
./mvnw clean package

# Запуск через Docker Compose (из корня проекта)
docker-compose up story-service
```

### Полный запуск всех сервисов
```bash
docker-compose up
```

## Конфигурация

Основные настройки в `application.yml`:
- Порт: 8083
- База данных: H2 (in-memory)
- Eureka: http://localhost:8761/eureka/

## Тестирование

Запуск тестов:
```bash
./mvnw test
```

## Мониторинг

- H2 Console: http://localhost:8083/h2-console
- Health Check: http://localhost:8083/story/health
- Eureka Dashboard: http://localhost:8761

## Интеграция

Сервис интегрируется с:
- API Gateway (порт 8080)
- Eureka Service Discovery (порт 8761)
- Другими микросервисами через REST API

## Безопасность

- Все эндпоинты требуют аутентификации (через API Gateway)
- Проверка прав доступа к операциям с историями
- Автоматическая очистка истекших данных
