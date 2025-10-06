# Руководство по запуску микросервисов бэкенда Flash Spring

Это руководство описывает различные способы запуска микросервисной архитектуры бэкенда проекта Flash Spring.

## Архитектура

Проект состоит из следующих микросервисов:
- **Service Discovery** (Eureka) - порт 8761
- **API Gateway** - порт 8080
- **Auth Service** - порт 8081
- **User Service** - порт 8082
- **Story Service** - порт 8083
- **Post Service** - порт 8084
- **Groups Service** - порт 8085
- **Notification Service** - порт 8086
- **Media Service** - порт 8087
- **Balance Service** - порт 8089
- **Tokens Service** - порт 8090
- **WebSocket Service** - порт 8091

## Предварительные требования

1. **Java 17** или выше
2. **Maven 3.6+**
3. **PostgreSQL** (для баз данных сервисов)
4. **Docker** и **Docker Compose** (опционально, для контейнеризации)

## Способ 1: Запуск через Docker Compose (Рекомендуемый)

### Шаг 1: Сборка образов
```bash
cd flash-backend
docker-compose build
```

### Шаг 2: Запуск всех сервисов
```bash
docker-compose up -d
```

### Шаг 3: Проверка статуса
```bash
docker-compose ps
```

### Шаг 4: Просмотр логов
```bash
# Логи всех сервисов
docker-compose logs -f

# Логи конкретного сервиса
docker-compose logs -f auth-service
```

### Шаг 5: Остановка сервисов
```bash
docker-compose down
```

## Способ 2: Запуск через Visual Studio Code

### Настройка рабочего пространства
1. Откройте VSCode
2. Файл → Open Workspace from File...
3. Выберите `flash-spring.code-workspace`

### Запуск сервисов
1. Перейдите во вкладку "Run and Debug" (Ctrl+Shift+D)
2. Выберите нужный микросервис из выпадающего списка
3. Нажмите "Start Debugging" (F5)

### Порядок запуска
1. **Service Discovery** - запустите первым
2. Остальные сервисы - в любом порядке после Service Discovery

### Сборка проектов
1. Перейдите во вкладку "Terminal" → "Run Task"
2. Выберите нужную задачу сборки:
   - "Build All Microservices"
   - "Build [Service Name]"

## Способ 3: Ручной запуск через Maven

### Шаг 1: Сборка всех сервисов
```bash
cd flash-backend
mvn clean compile
```

### Шаг 2: Запуск Service Discovery
```bash
cd service-discovery
mvn spring-boot:run
```

### Шаг 3: Запуск остальных сервисов (в отдельных терминалах)
```bash
# Auth Service
cd auth-service
mvn spring-boot:run

# User Service
cd user-service
mvn spring-boot:run

# Post Service
cd post-service
mvn spring-boot:run

# И так далее для каждого сервиса...
```

### Шаг 4: Проверка доступности
```bash
# Service Discovery
curl http://localhost:8761

# API Gateway
curl http://localhost:8080/actuator/health

# Auth Service
curl http://localhost:8081/actuator/health
```

## Способ 4: Запуск отдельных сервисов

### Для разработки конкретного сервиса
```bash
cd [service-name]
mvn spring-boot:run -Dspring.profiles.active=dev
```

### С отладкой
```bash
cd [service-name]
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## Конфигурация баз данных

### Локальная PostgreSQL
Убедитесь, что PostgreSQL запущен и созданы необходимые базы данных:

```sql
-- Создание баз данных для сервисов
CREATE DATABASE auth_service;
CREATE DATABASE user_service;
CREATE DATABASE post_service;
-- И так далее для каждого сервиса
```

### Конфигурация подключения
Каждый сервис имеет файл `src/main/resources/application.yml` с настройками базы данных:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/[service_name]
    username: postgres
    password: your_password
```

## Мониторинг и отладка

### Actuator endpoints
Каждый сервис предоставляет endpoints для мониторинга:
- `http://localhost:[port]/actuator/health` - состояние сервиса
- `http://localhost:[port]/actuator/info` - информация о сервисе
- `http://localhost:[port]/actuator/metrics` - метрики

### Eureka Dashboard
- URL: `http://localhost:8761`
- Показывает зарегистрированные сервисы и их статус

### Логи
Логи каждого сервиса выводятся в консоль. Для детального логирования:
```yaml
logging:
  level:
    ru.lomov.flashbackend: DEBUG
```

## Устранение неполадок

### Проблема: Сервис не регистрируется в Eureka
**Решение**: Убедитесь, что Service Discovery запущен первым и доступен по адресу `http://localhost:8761`

### Проблема: Ошибка подключения к базе данных
**Решение**:
1. Проверьте, что PostgreSQL запущен
2. Проверьте корректность учетных данных в `application.yml`
3. Убедитесь, что база данных существует

### Проблема: Порт уже занят
**Решение**: Измените порт в `application.yml` или освободите порт

### Проблема: Зависимости не собираются
**Решение**:
```bash
mvn clean install -DskipTests
```

## Производственный запуск

### Сборка JAR файлов
```bash
cd [service-name]
mvn clean package -DskipTests
```

### Запуск JAR файла
```bash
java -jar target/[service-name]-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Docker образы
```bash
cd [service-name]
docker build -t flash/[service-name] .
docker run -p [port]:[port] flash/[service-name]
```

## Полезные команды

```bash
# Остановка всех Java процессов
pkill -f spring-boot

# Очистка Docker
docker system prune -a

# Просмотр используемых портов
netstat -tulpn | grep :808

# Maven wrapper (если Maven не установлен)
./mvnw clean compile
```

## Контакты

При возникновении проблем обращайтесь к команде разработки или проверяйте логи сервисов для диагностики.
