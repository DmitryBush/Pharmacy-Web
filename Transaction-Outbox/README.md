# Библиотека реализующая паттерн проектирования Transactional Outbox

Данная библиотека используется во всех сервисах, где требуется передавать данные из одной базы данных в другую, 
предоставляя единый API

## Особенности

- Предоставление информации о CRUD операции произведенным с объектом
- Поддержка автоматической конфигурации `Spring Boot`

## Требования для использования

- Spring Boot версии 3.5.x+ для поддержки автоматической конфигурации

## Какие данные доступны в таблице `outbox_service_table`

- `operation_id` - Уникальный идентификатор операции
- `object_name` - Название сущности над которой была произведена операция
- `operation_type` - `CRUD` тип операции указанный 1 символом (например `c` - create и т.д.)
- `created_at` - Время создания операции
- `payload` - Запись сущности

## Как использовать библиотеку

1. Добавьте зависимость в проект
```declarative
    implementation(project(":Transaction-Outbox"))
```
2. Создайте сущность `outbox_service_table`
Данная библиотека использует отдельную сущность для создания записей и эффективного использования CDC систем.

Создать ее можно с помощью любого инструмента миграции (например `liquibase`):
```yaml
    - createTable:
        tableName: outbox_service_table
        columns:
          - column:
              name: operation_id
              type: UUID
              constraints:
                nullable: false
                primaryKey: true
          - column:
              name: object_name
              type: VARCHAR(255)
              constraints:
                nullable: false
          - column:
              name: operation_type
              type: VARCHAR(1)
              constraints:
                nullable: false
          - column:
              name: created_at
              type: TIMESTAMPTZ
              constraints:
                nullable: false
          - column:
              name: payload
              type: TEXT
              constraints:
                nullable: false
```
3. Включите конфигурирование

Для использования Transactional Outbox добавьте в конфигурационный файл основного проекта `@EnableOutboxService` 

Для **авто** конфигурирования достаточно аннотации `@EnableOutboxService`

В случае если в проекте более одного `PlatformTransactionManager`, используйте **ручное конфигурирование**.

Добавьте в `application.yaml` следующую конфигурацию:
```yaml
    outbox:
      transaction-manager-name: platformTransactionManagerBeanName
```