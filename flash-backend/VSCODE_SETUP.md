# Настройка Visual Studio Code для работы с микросервисами

Это руководство поможет вам настроить Visual Studio Code для эффективной работы с микросервисной архитектурой проекта.

## Созданные файлы конфигурации

### 1. `.vscode/settings.json`
- Настройки Java (JDK путь, автоматическая сборка, форматирование)
- Исключение ненужных файлов из поиска
- Настройки редактора (размер табуляции, пробелы)

### 2. `.vscode/launch.json`
- Конфигурации отладки для всех микросервисов:
  - API Gateway
  - Auth Service
  - User Service
  - Post Service
  - Notification Service
  - Media Service
  - Groups Service
  - Service Discovery
  - Flash Backend (Monolith)

### 3. `.vscode/tasks.json`
- Задачи сборки для каждого микросервиса
- Задача сборки всех сервисов
- Запуск тестов
- Установка зависимостей

### 4. `flash-spring.code-workspace`
- Рабочее пространство со всеми микросервисами
- Рекомендуемые расширения
- Настройки исключения файлов

## Рекомендуемые расширения VSCode

Установите следующие расширения для лучшей работы с проектом:

1. **Java Development**:
   - `vscjava.vscode-java-pack` - пакет для Java разработки
   - `pivotal.vscode-spring-boot` - поддержка Spring Boot
   - `vmware.vscode-spring-boot-dashboard` - дашборд Spring Boot
   - `vscjava.vscode-maven` - поддержка Maven

2. **Другие полезные расширения**:
   - `ms-vscode.vscode-json` - поддержка JSON
   - `redhat.vscode-yaml` - поддержка YAML
   - `ms-azuretools.vscode-docker` - поддержка Docker
   - `eamodio.gitlens` - улучшенная работа с Git

## Как использовать

### Открытие рабочего пространства
1. Откройте VSCode
2. Файл → Open Workspace from File...
3. Выберите `flash-spring.code-workspace`

### Запуск микросервисов
1. Перейдите во вкладку "Run and Debug" (Ctrl+Shift+D)
2. Выберите нужный микросервис из выпадающего списка
3. Нажмите "Start Debugging" (F5)

### Сборка проектов
1. Перейдите во вкладку "Terminal" → "Run Task"
2. Выберите нужную задачу сборки

### Полезные горячие клавиши
- `Ctrl+Shift+P` - палитра команд
- `Ctrl+` ` - открыть терминал
- `F5` - начать отладку
- `Ctrl+F5` - запуск без отладки

## Порты микросервисов

При запуске микросервисы будут доступны на следующих портах (по умолчанию):

- **API Gateway**: 8080
- **Auth Service**: 8081
- **User Service**: 8082
- **Post Service**: 8083
- **Notification Service**: 8084
- **Media Service**: 8085
- **Groups Service**: 8086
- **Service Discovery**: 8761
- **Flash Backend**: 8087

## Советы по работе

1. **Одновременный запуск**: Запускайте Service Discovery первым, затем другие сервисы
2. **Мониторинг**: Используйте Spring Boot Dashboard для мониторинга состояния сервисов
3. **Отладка**: Установите точки останова в нужных сервисах для отладки
4. **Логи**: Просматривайте логи каждого сервиса в отдельных терминалах

## Устранение проблем

Если возникают проблемы с Java:
1. Проверьте путь к JDK в `.vscode/settings.json`
2. Убедитесь, что Maven установлен и доступен в PATH
3. Выполните `mvn clean install` в корне проекта для установки зависимостей

Для проблем с отладкой:
1. Убедитесь, что порт 5005 не занят
2. Проверьте настройки firewall
