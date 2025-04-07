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
1. Configura el archivo `application.yml` con los detalles de conexión a MongoDB.
2. Modifica las variables en `terraform/variables.tf` según tus credenciales de AWS y parámetros del proyecto.

## Despliegue con Terraform
1. Inicializa Terraform:
   ```bash
   terraform init
   ```
2. Revisa el plan de despliegue:
   ```bash
   terraform plan
   ```
3. Aplica el despliegue:
   ```bash
   terraform apply
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
- El pipeline realiza el build y despliegue automático a ECS.

## Contribuciones
Las contribuciones son bienvenidas. Por favor, realiza un fork del repositorio y envía tu pull request.

## Licencia
Este proyecto está bajo la Licencia MIT.
