GymTracker API (Spring Boot)
Мини-TЗ для README.md

Цель
REST API для учета тренировок с:

Клиентами, тренерами, упражнениями

Статистикой прогресса

Стек
Java 17 + Spring Boot 3

PostgreSQL (Docker)

Swagger (документация)

Эндпоинты
text
POST /api/workouts – добавить тренировку  
GET /api/stats – получить статистику  
Запуск
Поднять БД:

bash
docker-compose up -d  
Запустить приложение:

bash
./mvnw spring-boot:run  
