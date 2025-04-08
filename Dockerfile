# Etapa de construcción
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# Pasar argumentos de entorno
ARG MONGO_USER
ARG MONGO_PASS
ARG MONGO_DB
ARG MONGO_HOST
ARG SERVER_PORT

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=builder /app/target/franquicia-api-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE ${SERVER_PORT}

# Definir variables de entorno
ENV MONGO_USER=${MONGO_USER}
ENV MONGO_PASS=${MONGO_PASS}
ENV MONGO_DB=${MONGO_DB}
ENV MONGO_HOST=${MONGO_HOST}
ENV SERVER_PORT=${SERVER_PORT}

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]