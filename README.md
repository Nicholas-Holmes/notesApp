A simple full-stack notes application built with Java, Spring Boot, and a Swing-based front-end.

Features
- User registration and login
- Note creation, editing, and deletion
- Responsive Swing UI
- Secure password handling
- Exception handling with custom messages
- JWT-based authentication for securing user sessions

Tech Stack
- Java 17
- Spring Boot
- Maven
- Swing (Java GUI)
- JPA/Hibernate
- JWT (JSON Web Tokens) for secure stateless authentication

Getting Started
- Clone the repository:
  git clone https://github.com/Nicholas-Holmes/notesApp.git

- Create a file named `application.properties` inside `src/main/resources/`

- Add the following fields to the `application.properties` file:
  - spring.datasource.url
  - spring.datasource.username
  - spring.datasource.password
  - spring.jpa.hibernate.ddl-auto
  - jwt.secret

- Set up your database and ensure it is running

- Build and run the application:
  mvn clean install
  mvn spring-boot:run
