# Gym Tracker

RESTful веб-приложение для учета тренировок в тренажерном зале. Система позволяет управлять клиентами, тренерами, тренировками и подходами к упражнениям.

## 🚀 Технологии и зависимости

*   **Язык:** Java 17+
*   **Фреймворк:** Spring Boot 3.×
*   **База данных:** PostgreSQL
*   **Миграции:** Flyway
*   **Persistence:** Spring Data JPA
*   **Тестирование:** JUnit 5, Mockito
*   **Утилиты:** Lombok, MapStruct
*   **Документация API:** Swagger (OpenAPI 3.0)
*   **Сборка и запуск:** Docker, Docker Compose

## 📋 Функциональность (API Endpoints)

Приложение предоставляет следующие REST endpoints:

### Клиенты (`/api/clients`)
*   `GET /api/clients` - Получить список всех клиентов
*   `POST /api/clients` - Создать нового клиента
*   `GET /api/clients/{id}` - Получить клиента по ID
*   `PUT /api/clients/{id}` - Обновить данные клиента
*   `DELETE /api/clients/{id}` - Удалить клиента

### Тренеры (`/api/coaches`)
*   `GET /api/coaches` - Получить список всех тренеров
*   `POST /api/coaches` - Создать нового тренера
*   `GET /api/coaches/{id}` - Получить тренера по ID
*   `PUT /api/coaches/{id}` - Обновить данные тренера
*   `DELETE /api/coaches/{id}` - Удалить тренера

### Тренировки (`/api/workouts`)
*   `GET /api/workouts` - Получить список всех тренировок
*   `POST /api/workouts` - Создать новую тренировку
*   `GET /api/workouts/{id}` - Получить тренировку по ID
*   `PUT /api/workouts/{id}` - Обновить данные тренировки
*   `DELETE /api/workouts/{id}` - Удалить тренировку

### Управление тренировками клиента (`/api/clients/{clientId}/workouts`)
*   `GET /api/clients/{clientId}/workouts` - Получить все тренировки клиента
*   `POST /api/clients/{clientId}/workouts` - Добавить новую тренировку клиенту
*   `GET /api/clients/{clientId}/workouts/{workoutId}` - Получить конкретную тренировку клиента
*   `PUT /api/clients/{clientId}/workouts/{workoutId}` - Обновить тренировку клиента
*   `DELETE /api/clients/{clientId}/workouts/{workoutId}` - Удалить тренировку у клиента

### Управление подходами в тренировке (`/api/clients/{clientId}/workouts/{workoutId}/exercises-set`)
*   `GET /api/clients/{clientId}/workouts/{workoutId}/exercises-set` - Получить все подходы для тренировки
*   `POST /api/clients/{clientId}/workouts/{workoutId}/exercises-set` - Добавить новый подход к тренировке
*   `GET /api/clients/{clientId}/workouts/{workoutId}/exercises-set/{exerciseSetId}` - Получить подход по ID
*   `PUT /api/clients/{clientId}/workouts/{workoutId}/exercises-set/{exerciseSetId}` - Обновить подход
*   `DELETE /api/clients/{clientId}/workouts/{workoutId}/exercises-set/{exerciseSetId}` - Удалить подход

## 🐳 Запуск приложения с помощью Docker Compose

Это самый простой способ запустить всё приложение целиком (Backend + БД).

**Предварительные требования:**
*   Установленный [Docker](https://docs.docker.com/get-docker/)
*   Установленный [Docker Compose](https://docs.docker.com/compose/install/)

**Инструкция:**

1.  **Клонируйте репозиторий** (или поместите `docker-compose.yml` в корень вашего проекта):
    ```bash
    git clone <your-repository-url>
    cd gym-tracker
    ```

2.  **Соберите и запустите контейнеры:** Эта команда соберет образ вашего приложения и запустит его вместе с PostgreSQL.
    ```bash
    docker-compose up --build
    ```
    *   Флаг `--build` принудительно пересобирает образ приложения из `Dockerfile`.
    *   Чтобы запустить в фоновом режиме (detached mode), добавьте флаг `-d`: `docker-compose up -d --build`.

3.  **Приложение и БД запущены!**
    *   **Ваше приложение** доступно по адресу: `http://localhost:8080`
    *   **Swagger UI** для изучения API доступен по адресу: `http://localhost:8080/swagger-ui.html`
    *   **База данных** PostgreSQL доступна на хосте `localhost`, порт `5432`.
        *   Имя БД: `GymTracker`
        *   Пользователь: `postgres`
        *   Пароль: `postgres`
        *   *Эти параметры можно изменить в файле `docker-compose.yml` и `application.properties`.*

4.  **Остановка приложения:**
    ```bash
    docker-compose down
    ```
