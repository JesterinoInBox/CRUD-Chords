# CRUD-Chords
This is my first CRUD-application, website with chords of songs on guitar
Built with Spring Boot and PostgreSQL. 

# Tech Stack

    Backend: Spring Boot 4.x, Spring MVC, Spring Data JPA

    Frontend: Thymeleaf, Bootstrap 5, JavaScript

    Database: PostgreSQL with Hibernate ORM

    Build Tool: Maven

    Java Version: 25

  # Installation Steps

   Clone the repository

    git clone https://github.com/yourusername/guitar-chords-crud.git
    cd guitar-chords-crud

 # Configure Database 
  Create database
  
    CREATE DATABASE chords_db;

  Configure Application
  
  Edit src/main/resources/application.properties:

    spring.datasource.url=jdbc:postgresql://localhost:5432/chords_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update

  # Build and Run

  Using Maven
  
    mvn clean package
    java -jar target/crud-chords-0.0.1-SNAPSHOT.jar

  Or run directly with Maven
  
    mvn spring-boot:run

  # Access the Application
  Open your browser and navigate to:

    http://localhost:8000
