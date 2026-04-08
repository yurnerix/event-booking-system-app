
# Веб-приложение для бронирования ресурсов по времени.

Проект реализован на Java + Spring Boot и включает:
- регистрацию и вход пользователей
- роли USER и ADMIN
- управление ресурсами
- создание и удаление бронирований
- уведомления
- журнал аудита
- HTML-интерфейс на Thymeleaf
- JSON API на базе Jackson

---

## Описание проекта

Event Booking System — это система бронирования ресурсов по времени.  
Пользователи могут просматривать доступные ресурсы и создавать бронирования, а администратор может управлять ресурсами и просматривать журнал действий в системе.

В качестве ресурсов могут использоваться:
- рабочие места
- переговорные комнаты
- оборудование
- конференц-залы

---

## Основной функционал

### Для пользователя (USER)
- регистрация
- вход в систему
- просмотр списка ресурсов
- создание бронирования
- просмотр только своих бронирований
- удаление своих бронирований
- просмотр своих уведомлений
- пометка уведомлений как прочитанных
- очистка своих уведомлений

### Для администратора (ADMIN)
- весь функционал пользователя
- создание ресурсов
- редактирование ресурсов
- удаление ресурсов
- просмотр всех бронирований
- изменение статуса бронирования
- просмотр audit logs
- очистка audit logs

---

## Технологии

- Java 17 / 21  
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Spring Data MongoDB
- Thymeleaf
- Jackson
- PostgreSQL
- MongoDB
- Maven

---

## Архитектура проекта

Проект построен по слоистой архитектуре:

- controller — обработка HTTP-запросов и HTML-страниц
- api — REST API и JSON-ответы
- service — бизнес-логика
- repository — доступ к данным
- dto — объекты передачи данных
- entity — сущности PostgreSQL
- document — документы MongoDB
- mapper — преобразование entity/document <-> DTO
- security — логика безопасности
- config — конфигурация приложения
- exception — пользовательские исключения
- enums — перечисления

---

## Базы данных

### PostgreSQL
Используется для хранения основной структурированной информации:
- пользователи
- роли
- ресурсы
- бронирования

### MongoDB
Используется для хранения:
- уведомлений
- журнала аудита (audit logs)

---

## Роли пользователей

### ROLE_USER
Обычный пользователь системы:
- может просматривать ресурсы
- может создавать и удалять свои бронирования
- может просматривать свои уведомления

### ROLE_ADMIN
Администратор системы:
- может управлять ресурсами
- может просматривать все бронирования
- может менять статус бронирования
- может просматривать журнал аудита

---

## Основные сущности

### User
Пользователь системы:
- username
- email
- password
- enabled
- roles

### Role
Роль пользователя:
- ROLE_USER
- ROLE_ADMIN

### Resource
Ресурс для бронирования:
- name
- description
- type
- location
- capacity
- active
- availableFrom
- availableTo

### Booking
Бронирование:
- user
- resource
- startTime
- endTime
- status
- purpose

### NotificationDocument
Уведомление в MongoDB:
- userId
- type
- message
- read
- createdAt

### AuditLogDocument
Запись аудита в MongoDB:
- actionType
- userId
- bookingId
- resourceId
- details
- timestamp

---

## Реализованные проверки

В приложении реализована бизнес-логика проверки бронирований:

- время начала должно быть раньше времени окончания
- бронирование должно попадать в диапазон доступности ресурса
- нельзя забронировать уже занятый слот
- пользователь не может удалить или изменить чужое бронирование
- пользователь не видит чужие уведомления
- пользователь не имеет доступа к административным операциям

---

## Паттерны проектирования

В проекте используются следующие паттерны:

### Repository Pattern
Используется в слое repository:
- UserRepository
- RoleRepository
- ResourceRepository
- BookingRepository
- NotificationRepository
- AuditLogRepository

### Service Layer Pattern
Используется в слое service.impl:
- AuthServiceImpl
- ResourceServiceImpl
- BookingServiceImpl
- NotificationServiceImpl
- AuditLogServiceImpl

### DTO Pattern
Используется в пакете dto:
- RegisterRequest
- CreateResourceRequest
- UpdateResourceRequest
- CreateBookingRequest
- ResourceResponse
- BookingResponse
- NotificationResponse
- AuditLogResponse

### Mapper Pattern
Используется в пакете mapper:
- ResourceMapper
- BookingMapper
- NotificationMapper

### Dependency Injection
Используется через Spring для внедрения зависимостей в:
- контроллеры
- сервисы
- конфигурационные классы

---

## SOLID в проекте

### Single Responsibility Principle
Каждый класс отвечает за одну основную задачу:
- контроллеры — за HTTP и страницы
- сервисы — за бизнес-логику
- репозитории — за доступ к данным
- мапперы — за преобразование объектов

### Open/Closed Principle
Проект можно расширять:
- новыми типами ресурсов
- новыми типами аудита
- новыми API-эндпоинтами
- новыми ролями

### Interface Segregation Principle
Сервисы разделены на отдельные интерфейсы:
- AuthService
- BookingService
- ResourceService
- NotificationService
- AuditLogService

### Dependency Inversion Principle
Контроллеры и сервисы зависят от абстракций, а не от конкретной реализации.

---

## HTML-интерфейс

В проекте реализованы страницы на Thymeleaf:

- index.html
- login.html
- register.html
- resources.html
- create-resource.html
- edit-resource.html
- bookings.html
- create-booking.html
- notifications.html
- audit-logs.html

---

## JSON API

В проекте добавлены REST API-эндпоинты:

### Resources
- GET /api/resources
- GET /api/resources/{id}

### Bookings
- GET /api/bookings
- GET /api/bookings/{id}

### Notifications
- GET /api/notifications

### Audit logs
- GET /api/audit-logs

JSON-ответы формируются с помощью Jackson.

---

## Как запустить проект локально

### 1. Клонировать репозиторий

```bash
git clone https://github.com/yurnerix/event-booking-system-app.git
cd event-booking-system-app
```
### 2. Настроить SQL
```SQL
CREATE DATABASE event-booking-db
```
### 3. Настроить MongoDB
```SQL
event_booking_mongo
```
### 4. Проверить application.yaml
Указать свои настройки для подключения к PostgreSQL и MongoDB
```YAML
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/event-booking-db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver

  data:
    mongodb:
      uri: mongodb://localhost:27017/event_booking_mongo

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  thymeleaf:
    cache: false
```
### 5. Запустить приложение
```bash
mvn spring-boot:run
```

