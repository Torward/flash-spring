# Product Service

Микросервис для управления товарами в системе Flash.

## Функциональность

- Создание, обновление, удаление товаров
- Поиск товаров по категориям, типу, цене
- Полнотекстовый поиск товаров
- Получение товаров пользователя
- Получение последних добавленных товаров

## API Endpoints

### Товары
- `POST /products` - Создать товар
- `PUT /products/{id}` - Обновить товар
- `DELETE /products/{id}` - Удалить товар
- `GET /products/{id}` - Получить товар по ID
- `GET /products` - Получить все товары (с пагинацией)
- `GET /products/category/{category}` - Товары по категории
- `GET /products/type/{type}` - Товары по типу
- `GET /products/search?query={query}` - Поиск товаров
- `GET /products/price-range?minPrice={min}&maxPrice={max}` - Товары по диапазону цен
- `GET /products/user/{userId}` - Товары пользователя
- `GET /products/latest` - Последние добавленные товары

## Запуск

1. Убедитесь, что запущены:
   - Eureka Server
   - MySQL база данных

2. Настройте базу данных в `application.yml`

3. Запустите сервис:
```bash
cd product-service
mvn spring-boot:run
```

## Docker

Сборка Docker образа:
```bash
docker build -t product-service .
```

Запуск контейнера:
```bash
docker run -p 8080:8080 product-service
```

## Конфигурация

Основные настройки в `application.yml`:
- Порт: 8080
- База данных: MySQL
- Service discovery: Eureka Client
