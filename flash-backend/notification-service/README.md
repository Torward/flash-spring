# Notification Service

## Prohibition Notice
The use of AWS services (including S3) and Firebase services is prohibited in this project. Please refrain from implementing or integrating these services in any module.

Микросервис для управления уведомлениями в системе Flash.

## Функциональность

- **Управление уведомлениями**: создание, чтение, обновление и удаление уведомлений
- **WebSocket поддержка**: реальные уведомления через WebSocket
- **Интеграция с Eureka**: регистрация в сервисе обнаружения
- **База данных**: PostgreSQL для хранения уведомлений
- **API**: RESTful API для управления уведомлениями

## Технологии

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring WebSocket
- PostgreSQL
- Spring Cloud Netflix Eureka
- Lombok
- Swagger/OpenAPI

## Запуск

### Требования
- Java 17
- Maven
- PostgreSQL
- Сервис обнаружения Eureka

### Настройка базы данных

Создайте базу данных в PostgreSQL:

```sql
CREATE DATABASE notification_db;
```

### Запуск приложения

1. Клонируйте репозиторий
2. Перейдите в директорию notification-service
3. Соберите проект: `mvn clean package`
4. Запустите приложение: `java -jar target/notification-service-1.0.0.jar`

Или используйте Maven:

```bash
mvn spring-boot:run
```

## API Endpoints

### Получить уведомления пользователя
```
GET /api/notifications/user/{userId}
```

### Отметить уведомление как прочитанное
```
POST /api/notifications/{notificationId}/read/user/{userId}
```

### Удалить уведомление
```
DELETE /api/notifications/{notificationId}/user/{userId}
```

### Проверка здоровья сервиса
```
GET /api/notifications/health
```

## WebSocket

Сервис поддерживает WebSocket для реальных уведомлений:

- **Endpoint**: `/ws/notifications`
- **Топики**:
  - `/topic/notifications/{userId}` - уведомления для конкретного пользователя
  - `/topic/notifications` - уведомления для всех пользователей
  - `/topic/notification-count/{userId}` - количество непрочитанных уведомлений

## Конфигурация

Основные настройки в `application.yml`:

```yaml
server:
  port: 8084

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/notification_db
    username: postgres
    password: password

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Docker

Для запуска в Docker:

```bash
docker build -t notification-service .
docker run -p 8084:8084 notification-service
```

## Мониторинг

Сервис предоставляет эндпоинты для мониторинга через Spring Boot Actuator:

- `/actuator/health` - состояние сервиса
- `/actuator/info` - информация о сервисе
- `/actuator/metrics` - метрики приложения
