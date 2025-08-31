# Руководство по установке Maven и компиляции проекта

## Установка Maven

Maven уже установлен в системе по пути: `C:\Папка Папок\flash-spring\api-gateway\maven\apache-maven-3.9.11\`

Путь к Maven добавлен в переменную среды PATH.

## Проверка установки

Для проверки установки выполните команду:
```bash
mvn --version
```

Ожидаемый вывод:
```
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: C:\Папка Папок\flash-spring\api-gateway\maven\apache-maven-3.9.11
Java version: 20.0.2 vendor: Oracle Corporation runtime: C:\Program Files\Java\jdk-20
Default locale: ru_RU platform encoding: UTF-8
OS name: "windows 10" version: "10.0" arch: "amd64" family: "windows"
```

## Структура проекта

Проект представляет собой микросервисную архитектуру со следующими сервисами:

- **api-gateway** - API Gateway (текущий проект)
- **user-service** - Сервис управления пользователями
- **story-service** - Сервис управления историями
- **notification-service** - Сервис уведомлений
- **reels-service** - Сервис рилов
- **live-service** - Сервис прямых трансляций
- **podcast-service** - Сервис подкастов
- **party-service** - Сервис вечеринок

## Компиляция отдельных сервисов

### Компиляция API Gateway
```bash
cd c:/Папка Папок/flash-spring/api-gateway
mvn clean compile
```

### Компиляция User Service
```bash
cd c:/Папка Папок/flash-spring/user-service
mvn clean compile
```

### Компиляция Story Service
```bash
cd c:/Папка Папок/flash-spring/story-service
mvn clean compile
```

### Компиляция Notification Service
```bash
cd c:/Папка Папок/flash-spring/notification-service
mvn clean compile
```

### Компиляция других сервисов
Аналогичным образом можно скомпилировать другие сервисы, перейдя в соответствующие директории.

## Сборка JAR файлов

Для создания исполняемых JAR файлов используйте команду:
```bash
mvn clean package
```

Эта команда создаст JAR файл в директории `target/` каждого сервиса.

## Запуск приложений

После сборки JAR файлов приложения можно запустить с помощью команды:
```bash
java -jar target/название-сервиса-1.0.0.jar
```

## Полезные Maven команды

- `mvn clean` - очистка проекта
- `mvn compile` - компиляция исходного кода
- `mvn test` - запуск тестов
- `mvn package` - сборка JAR файла
- `mvn install` - установка артефакта в локальный репозиторий
- `mvn spring-boot:run` - запуск Spring Boot приложения (если настроен Spring Boot plugin)

## Настройка переменных среды

Для удобства использования Maven из любой директории, убедитесь, что путь к Maven добавлен в переменную среды PATH:

```
C:\Папка Папок\flash-spring\api-gateway\maven\apache-maven-3.9.11\bin
```

## Примечания

1. Проект использует Java 17+ (в системе установлена Java 20)
2. Все сервисы используют Spring Boot 3.2.0 и Spring Cloud 2023.0.0
3. Для работы с Eureka Server требуется запуск сервера обнаружения сервисов
4. Некоторые сервисы могут требовать дополнительные настройки (базы данных, брокеры сообщений и т.д.)

## Устранение проблем

Если возникают проблемы с компиляцией:
1. Убедитесь, что Java установлена корректно: `java -version`
2. Проверьте путь к Maven: `mvn --version`
3. Очистите проект: `mvn clean`
4. Обновите зависимости: `mvn dependency:resolve`
