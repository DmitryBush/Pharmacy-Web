# Pharmacy Web
[![ru](https://img.shields.io/badge/lang-ru-red.svg)](../README.md)
## Description
This is a fullstack (backend-oriented) information system
for automating business processes of a pharmacy chain.
This application includes an administration panel
and an interface for the end user.
## Technology stack
Backend:
-  Java 21+
-  Spring Boot
-  Spring Data JPA
-  Spring Web
-  Spring Security
-  Spring State Machine (управление состояниями заказов)

Frontend:
- HTML5
- CSS3
- JavaScript

Database:
- PostgreSQL

## Features
### Admin panel
#### Product management
The system can offer all CRUD operations for product management with search function.
#### Product type management
- Hierarchical system of categories (e.g.: "Medicines → Antibiotics")
- Moving type in the hierarchy
- Managing Subtypes
#### Order management
- Role-based access control
- Completion, cancellation, return of order
#### Warehouse management
- Registration of receipt and sale of goods
- Warehouse inventory tracking

## Установка и запуск
1. Clone the repository

`git clone https://github.com/DmitryBush/Pharmacy-Web.git`

2. Set up the application configuration in `resources/application.yml`

In `spring.datasource`, change the `url`, `username` and `password` parameters to your own:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testbase
    username: test
    password: test
```
3. Launch the application

Build and run the project
```shell
./gradlew build
./gradlew bootrun
```

4. Access the application
- Admin panel: `http://localhost:8080/admin/dashboard` ([Interface example](dashboard.png))
- Main page: `http://localhost:8080`

## Roadmap
### Admin panel
- [x] Warehouse management (receipt/sale)
- [x] Order management
- [x] Product type management
- [x] Product management
- [ ] Staff management
### Client interface
- [ ] Main page
- [ ] Product catalog
- [ ] Order creation
- [ ] Shopping cart

## License
This project is distributed under the license GNU General Public License v3.0. For more details see the file [LICENSE](../LICENSE).