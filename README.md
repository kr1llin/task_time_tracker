# Task Time Tracker API

REST-сервис учета рабочего времени сотрудников

## Функционал

 **Управление задачами**
  - Создание задачи (название, описание)
  - Получение задачи по ID
  - Изменение статуса задачи (NEW, IN_PROGRESS, DONE)

 **Учёт времени**
  - Создание записи о затраченном времени (ID сотрудника, ID задачи, начало/конец периода, описание)
  - Получение всех записей сотрудника за заданный период

 **Документация API**
  - Swagger UI (SpringDoc OpenAPI)

 **Валидация** входных DTO, глобальная обработка ошибок.


## Как запустить

### 1. Docker compose

```bash
git clone https://github.com/kr1llin/task_time_tracker.git && cd task_time_tracker

#запуск
docker-compose up -d --build

#остановка
docker-compose down
```
### 2. Без Docker

```bash
git clone https://github.com/kr1llin/task_time_tracker.git && cd task_time_tracker

mvn clean package

java -jar target/task_time_tracker-1.0.jar
```

### API доступно: http://localhost:8080

### Документация: http://localhost:8080/swagger-ui/index.html

### Postman коллекция: https://github.com/kr1llin/task_time_tracker/blob/2270c1b27742f841da79fb695d8b2f745c6bb7a8/time_tracker.postman_collection.json

<img width="1059" height="1032" alt="image" src="https://github.com/user-attachments/assets/dba47b73-1760-4614-9812-625f307006f1" />

