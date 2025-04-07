# Franquicia API

## Descripción del Proyecto
Franquicia API es una aplicación basada en Spring Boot que gestiona la información de franquicias. El backend está desarrollado en Java utilizando Spring WebFlux para soportar aplicaciones reactivas. El almacenamiento se realiza en MongoDB (DocumentDB en AWS), y el despliegue se realiza en AWS ECS utilizando contenedores Docker.

## Requisitos Previos
- Docker
- Terraform
- AWS CLI configurado
- Git
- Java 17+
- Maven

## Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/lams1989/Franquicia.git
   cd Franquicia
   ```
2. Construye el proyecto usando Maven:
   ```bash
   mvn clean install
   ```
3. Construye la imagen Docker:
   ```bash
   docker build -t franquicia-api .
   ```

## Configuración
1. Ajusta el archivo application.yml con la cadena de conexión a MongoDB que se generará tras el despliegue en AWS. El archivo se encuentra en: `src/main/resources/application.yml`
   ejemplo de configuración:
   spring:
      data:
        mongodb:
          uri: mongodb://franquicia_user:franquicia123@<mongo_public_ip>:27017/franquicia_db?authSource=admin
   server:
    port: 8080


## Despliegue con Terraform
1. Modifica el archivo terraform/variables.tf con tus credenciales y parámetros:
   variable "aws_region" {
      description = "Región de AWS"
      default     = "us-east-1"
   }

   variable "key_name" {
      description = "Nombre de la clave SSH"
      default     = "franquicia-key"
   }

2. Inicializa Terraform:
   ```bash
   terraform init
   ```
3. Revisa el plan de despliegue:
   ```bash
   terraform plan
   ```
4. Aplica el despliegue:
   ```bash
   terraform apply
   ```
5. Verificar la Infraestructura:
   ```bash
   terraform output
   ```
6. Conectar a la instancia EC2:
   ```bash
   mongo "mongodb://franquicia_user:franquicia123@localhost:27017/franquicia_db?authSource=admin"
   ```

## Ejecución en Local
1. Levanta el contenedor Docker:
   ```bash
   docker-compose up
   ```
2. Accede al API:
   ```bash
   curl http://localhost:8080/api/franquicias
   ```

## Despliegue en AWS ECS
1. Asegúrate de haber ejecutado Terraform correctamente.
2. Verifica los logs desde CloudWatch para confirmar el despliegue.

## Automatización con GitHub Actions
- El pipeline se encuentra en `.github/workflows/deploy.yml`
- Incluye: Construcción de la imagen Docker, Despliegue automático en AWS ECS y Verificación de estado del despliegue.
- El pipeline se activa al realizar un push en la rama principal (main).

## Verificación de estado del despliegue.
- btener IP Pública:
  ```bash
   terraform output mongo_public_ip
   ```

- Verificar API en Producción:
  ```bash
   curl http://<mongo_public_ip>:8080/api/franquicias
   ```
  
## Acceso a MongoDB Remoto desde tu Máquina:
- Si tienes el cliente de MongoDB instalado en tu equipo:
   ```bash
   mongo "mongodb://franquicia_user:franquicia123@<mongo_public_ip>:27017/franquicia_db?authSource=admin"
   ```
  
## Contribuciones
Las contribuciones son bienvenidas. Por favor, realiza un fork del repositorio y envía tu pull request.

## Licencia
Este proyecto está bajo la Licencia MIT.
