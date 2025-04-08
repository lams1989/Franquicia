#################################
# 1. Módulo VPC
#################################
module "vpc" {
  source         = "./modules/vpc"
  name           = "${var.project_name}-vpc"
  vpc_cidr_block = "10.0.0.0/16"
  azs            = var.availability_zones
}

#################################
# 2. Módulo ALB
#################################
module "alb" {
  source         = "./modules/alb"
  name           = "${var.project_name}-alb"
  vpc_id         = module.vpc.vpc_id
  public_subnets = module.vpc.public_subnets
}

#################################
# 3. Módulo ECR
#################################
module "ecr" {
  source    = "./modules/ecr"
  repo_name = "${var.project_name}-repo"
}

#################################
# 4. Módulo ECS
#################################
module "ecs" {
  source           = "./modules/ecs"
  ecs_cluster_name = "${var.project_name}-ecs-cluster"
}

#################################
# 5. Módulo DOCUMENTDB
#################################
module "documentdb" {
  source            = "./modules/documentdb"
  name              = "${var.project_name}-docdb"
  vpc_id            = module.vpc.vpc_id
  public_subnets    = module.vpc.public_subnets
  ecs_tasks_sg_id   = module.fargate_service.ecs_tasks_sg_id
  master_username   = "adminuser"
  master_password   = "AdminPassword123!"  # <--- Ajusta a tu preferencia
  instance_class    = "db.t4g.medium"
  # Nota: Debemos definir `ecs_tasks_sg_id` después de crearlo en fargate_service.
  # Sin embargo, FARGATE_SERVICE depende también del ALB.
  # Para resolver esta dependencia, haremos un pequeño truco:
  # Llamamos primero al fargate_service sin docdb config, luego referenciamos.
}

#################################
# 6. Módulo FARGATE_SERVICE
#################################
module "fargate_service" {
  source           = "./modules/fargate_service"
  service_name     = "${var.project_name}-service"
  image_url        = "${module.ecr.repo_url}:latest"    # Ajusta tag si necesitas
  cluster_id       = module.ecs.ecs_cluster_id
  vpc_id           = module.vpc.vpc_id
  public_subnets   = module.vpc.public_subnets
  alb_arn          = module.alb.alb_arn
  alb_sg_id        = module.alb.alb_sg_id
  container_port   = 8080
  listener_port    = 80
  desired_count    = 1
  public_sg_id     = module.sg_group.public_sg_id
  # Optional roles
  execution_role_arn = "<arn-de-tu-rol-ejecucion>"
  task_role_arn      = "<arn-de-tu-task-role>"
}

#################################
# 4. Módulo sg_group
#################################
module "sg_group" {
  source           = "./modules/sg_group"
  vpc_id           = module.vpc.vpc_id
  project_name     = var.project_name
}

# Ajuste de Dependencias entre Fargate y DocumentDB
# ======================================================
# Si tu microservicio debe conectarse a DocumentDB al iniciarse,
# lo ideal es que DocumentDB esté listo antes. Podrías forzar
# un "depends_on" en la definición fargate_service si fuera crítico:
#
# Ejemplo:
# resource "aws_ecs_service" "this" ... {
#    ...
#    depends_on = [aws_docdb_cluster_instance.primary]
# }
#
# O, de manera más simple en Terraform 1.x, definimos la dependencia
# en el main:
#
# El fargate_service depende de documentdb:
#
# Esto evita que Fargate inicie antes de que DocDB esté disponible.
depends_on = [
  module.documentdb
]
