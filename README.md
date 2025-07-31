# Hotel Management System
> Разработано на основе [технического задания](./task.txt).
## Технологии
- Maven
- Java 17
- Spring Boot 3.5
- Spring JPA
- Liquibase
- H2

---
## Профили
- `postgres` - для быстрой смены БД на PostgreSQL

---
## Запуск

### H2
1. Клонируйте репозиторий и перейдите в каталог проекта
    ```bash
    git clone https://github.com/lamashkevich/hotel-management-system.git
    cd hotel-management-system
    ```
2. Запустите приложение
   ```bash
   mvn spring-boot:run
   ```
   
### Postgres
Для запуска приложения с использованием PostgreSQL выполните следующие шаги:
1. Клонируйте репозиторий и перейдите в каталог проекта
    ```bash
    git clone https://github.com/lamashkevich/hotel-management-system.git
    cd hotel-management-system
    ```
2.  Создайте файл `.env` в корне проекта с переменными для Docker Compose
    ```
    POSTGRES_PASSWORD=root
    POSTGRES_USER=postgres
    ```
> Эти переменные используются только Docker Compose для запуска контейнера с базой данных
3. Запустите PostgreSQL в контейнере
    ```bash
    docker compose up --build -d
    ```
4. Запустите приложение с указанием профиля и переменных
    ```bash
    export POSTGRES_USER=postgres
    export POSTGRES_PASSWORD=root
    mvn spring-boot:run "-Dspring-boot.run.profiles=postgres"
    ```
---
## Swagger
- Swagger-ui: http://localhost:8092/swagger.html
- Api-docs: http://localhost:8092/api-docs

---
## Контакты
- Yauheni Lamashkevich - [@lamashkevich](https://github.com/lamashkevich)