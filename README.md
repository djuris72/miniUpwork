# MiniUpwork

A simplified freelance marketplace platform inspired by Upwork, built with Spring Boot, Thymeleaf, and MySQL.

The application allows users to create projects, submit proposals, manage categories and skills, and leave reviews, demonstrating a complete CRUD-based web application using the Spring ecosystem.

---

## Features

### User Management
- Create users
- Update user information
- View user profiles
- Delete users

### Project Management
- Create projects
- Edit projects
- View project details
- Delete projects
- Track project status

### Proposal Management
- Submit proposals for projects
- View submitted proposals
- Manage proposal statuses

### Categories
- Create project categories
- View categories
- Organize projects by category

### Skills
- Manage user skills
- Associate skills with users

### Reviews
- Create reviews
- View reviews
- Rate users based on completed work

---

## Technologies Used

### Backend
- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML5
- CSS3

### Database
- MySQL

### Build Tool
- Maven

---

## Project Architecture

```text
Client Browser
       │
       ▼
Thymeleaf Views
       │
       ▼
Controllers
       │
       ▼
Services
       │
       ▼
Repositories
       │
       ▼
MySQL Database
```

---

## Project Structure

```text
src/main/java
│
├── controller
│   ├── REST Controllers
│   └── Web Controllers
│
├── service
│
├── repository
│
├── model
│
├── dto
│
├── enums
│
└── exception

src/main/resources
│
├── templates
│   ├── projects
│   ├── proposals
│   ├── reviews
│   └── categories
│
└── application.properties
```

---

## Database Configuration

Configure MySQL connection in:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/miniupwork_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
```

Create the database before starting the application:

```sql
CREATE DATABASE miniupwork_db;
```

---

## Installation

### Clone Repository

```bash
git clone https://github.com/yourusername/miniUpwork.git
cd miniUpwork
```

### Install Dependencies

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

---

## Access Application

After startup, open:

```text
http://localhost:8080
```

---

## Main Entities

### User
Represents freelancers and clients within the system.

### Project
Represents freelance projects posted on the platform.

### Proposal
Represents bids submitted by freelancers.

### Category
Groups projects into logical categories.

### Skill
Represents user competencies and expertise.

### Review
Stores feedback and ratings for completed work.

---

## Learning Objectives

This project demonstrates:

- Spring Boot application development
- MVC architecture
- CRUD operations
- Thymeleaf templating
- JPA and Hibernate integration
- MySQL database management
- DTO pattern usage
- Exception handling
- Layered application architecture

---

## Future Improvements

- User authentication and authorization
- Spring Security integration
- Role-based access control
- Project search and filtering
- File uploads
- Messaging system
- REST API documentation with Swagger
- Docker deployment
- Unit and integration tests

---

## License

This project was developed for educational purposes.

MIT License
