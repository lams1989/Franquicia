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
   git clone https://github.com/usuario/franquicia-api.git
   cd franquicia-api
   ```
2. Compilar el proyecto:
   ```bash
   mvn clean package -DskipTests
   ```
3. Construir el contenedor:
   ```bash
   docker-compose build
   ```

## Ejecución
1. Levantar el entorno con Docker Compose:
    ```bash
    docker-compose up -d
   ```
2. Ver los logs:
    ```bash
    docker-compose logs -f franquicia-api
   ```

## NOTA: conexion a base de datos automatica, el cluster publico ya se encuentra configurado parasu respectiva conexion
    `Cadena conexion cluster: mongodb+srv://luisalejandromunozsierra:123456789@cluster0.qmlyk1u.mongodb.net/`


## APIs Disponibles: La aplicación Franquicia API expone una serie de endpoints para gestionar franquicias, sucursales y productos. A continuación se detallan las rutas, métodos y ejemplos de uso.
1. Obtener todas las franquicias
   curl -X GET "http://localhost:8080/api/franquicias" -H "Content-Type: application/json"

2. Obtener una franquicia por ID
   curl -X GET "http://localhost:8080/api/franquicias/franq1" -H "Content-Type: application/json"
   Cuerpo de la solicitud:

   ` {
    "id": "franq2",
    "nombre": "Franquicia Nueva"
    }`

3. Crear una nueva franquicia
   curl -X POST "http://localhost:8080/api/franquicias" -H "Content-Type: application/json" -d '{"id": "franq2", "nombre": "Franquicia Nueva"}'
   Cuerpo de la solicitud:

   `{
   "id": "franq2",
   "nombre": "Franquicia Nueva"
   }`

4. Sucursales
   curl -X POST "http://localhost:8080/api/franquicias/franq1/sucursales" -H "Content-Type: application/json" -d '{"id": "suc2", "nombre": "Sucursal Secundaria"}'
   Cuerpo de la solicitud:

 `  {
   "id": "suc2",
   "nombre": "Sucursal Secundaria"
   }`

5. Actualizar nombre de una sucursal
   curl -X PUT "http://localhost:8080/api/franquicias/franq1/sucursales/suc2" -H "Content-Type: application/json" -d '{"nombre": "Sucursal Actualizada"}'

   Cuerpo de la solicitud:

 `   {
   "nombre": "Sucursal Actualizada"
   }`

6. Agregar producto a una sucursal
   curl -X POST "http://localhost:8080/api/franquicias/franq1/sucursales/suc1/productos" -H "Content-Type: application/json" -d '{"id": "prod2", "nombre": "Producto B", "stock": 50}'

   Cuerpo de la solicitud:

`   {
   "id": "prod2",
   "nombre": "Producto B",
   "stock": 50
   }`
   
7. Eliminar un producto de una sucursal
   curl -X DELETE "http://localhost:8080/api/franquicias/franq1/sucursales/suc1/productos/prod2"

    Cuerpo de la solicitud:

 `  {
   "id": "prod2",
   "nombre": "Producto B",
   "stock": 50
   }`

8. Eliminar un producto de una sucursal
   curl -X DELETE "http://localhost:8080/api/franquicias/franq1/sucursales/suc1/productos/prod2"

9. Modificar stock de un producto
   curl -X PATCH "http://localhost:8080/api/franquicias/franq1/sucursales/suc1/productos/prod2/stock" -H "Content-Type: application/json" -d '{"stock": 150}'
   Cuerpo de la solicitud:

  ` {
   "stock": 150
   }`

10. Obtener el producto con mayor stock
    curl -X GET "http://localhost:8080/api/franquicias/franq1/productos/mayor-stock"

##Respuesta en Caso de Error
En caso de error, el servicio devuelve una estructura JSON con el código de error y el mensaje descriptivo:

    {
    "timestamp": "2025-04-08T12:45:34.125+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Franquicia no encontrada",
    "path": "/api/franquicias/franq999"
    }

## Infraestructura con Terraform
Este archivo describe cómo usar los archivos y módulos de Terraform para crear una infraestructura base que incluye:

    VPC con subnets públicas

    Security Groups para ALB y ECS/DocumentDB

    Application Load Balancer (ALB)

    Amazon ECR (repositorio de contenedores)

    Amazon ECS (cluster)

    Fargate Service (servicio que ejecuta un contenedor)

    Amazon DocumentDB (base de datos compatible con MongoDB)

    IAM Roles (ECS Task Execution Role, Task Role)

Estructura de Archivos

* ├── main.tf              # Archivo principal que llama a los módulos
* ├── provider.tf          # Configuración del proveedor AWS
* ├── variables.tf         # Variables globales
* ├── outputs.tf           # Outputs globales (ALB DNS, DocumentDB endpoint, etc.)
* ├── terraform.tfvars     # Valores de variables concretos (opcional)
* └── modules/
* ├── vpc/             # Módulo VPC
* ├── alb/             # Módulo ALB
* ├── ecr/             # Módulo ECR
* ├── ecs/             # Módulo ECS Cluster
* ├── fargate_service/ # Módulo para la definición del servicio ECS Fargate
* ├── documentdb/      # Módulo para Amazon DocumentDB
* ├── sg_group/        # Módulo para SG único (si aplica)
* └── iam_roles/       # Módulo para la creación de Roles IAM (ej. ECS Task Execution Role)

  2. Pasos para Ejecutar
       ```bash
      aws configure
     ```
  
  3. Inicializar Terraform
       ```bash
      terraform init
     ```
  
  4.  Verificar el Plan
       ```bash
      terraform init
        ```
    
  5. Aplicar Cambios
      ```bash
     terraform apply
     ```
  6. Validar la Infraestructura
     Una vez completado, Terraform mostrará un mensaje de éxito. Revisa:

    alb_dns_name (output) para acceder al servicio Fargate.

    docdb_endpoint (output) para la conexión a DocumentDB.
  
  7. Destruir la Infraestructura
     Cuando finalices las pruebas o quieras liberar costos, ejecuta:
    ```bash
     terraform destroy
     ```

8. Problemas Comunes
   Self-Referential Block en SG:

   Usa self = true en lugar de referenciar el SG a sí mismo.

    ARN Inválido para Roles:
    Asegúrate de que task_role_arn y execution_role_arn sean ARNs reales o crea los roles via Terraform.

    Subnets Privadas vs Públicas:
    Este proyecto ubica DocumentDB y Fargate en subnets públicas (PoC). En producción, usa subnets privadas y NAT gateway.
 
   

## Configurar Credenciales de AWS
   Antes de usar Terraform, debes contar con credenciales de AWS con los permisos adecuados:

## Contribuciones
Las contribuciones son bienvenidas. Por favor, realiza un fork del repositorio y envía tu pull request.

## Licencia
Este proyecto está bajo la Licencia MIT.
