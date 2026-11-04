Levantamiento de Base de datos localmente, modificar el archivo application.properties con esto: 
spring.application.name=loginabc

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/loginabc
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# MySQL Dialect
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Servidor
server.port=8080
