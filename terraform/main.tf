#################################
# 1. iam_roles
#################################
module "iam_roles" {
  source = "./modules/iam_roles"
}

#################################
# 2. Módulo VPC
#################################
module "vpc" {
  source         = "./modules/vpc"
  name           = "${var.project_name}-vpc"
  vpc_cidr_block = "10.0.0.0/16"
  azs            = var.availability_zones
}

#################################
# 3. Módulo ALB
#################################
module "alb" {
  source         = "./modules/alb"
  name           = "${var.project_name}-alb"
  vpc_id         = module.vpc.vpc_id
  public_subnets = module.vpc.public_subnets
}

#################################
# 4. Módulo ECR
#################################
module "ecr" {
  source    = "./modules/ecr"
  repo_name = "${var.project_name}-repo"
}

#################################
# 5. Módulo ECS
#################################
module "ecs" {
  source           = "./modules/ecs"
  ecs_cluster_name = "${var.project_name}-ecs-cluster"
}

#################################
# 5. Módulo sg_group
#################################
module "sg_group" {
  source        = "./modules/sg_group"
  vpc_id        = module.vpc.vpc_id
  project_name  = var.project_name
}

#################################
# 6. Módulo FARGATE_SERVICE
##################################
module "fargate_service" {
  source           = "./modules/fargate_service"
  service_name     = "${var.project_name}-service"
  image_url        = "${module.ecr.repo_url}:latest"
  cluster_id       = module.ecs.ecs_cluster_id
  vpc_id           = module.vpc.vpc_id
  public_subnets   = module.vpc.public_subnets
  alb_arn          = module.alb.alb_arn
  alb_sg_id        = module.alb.alb_sg_id

  execution_role_arn = module.iam_roles.ecs_task_execution_role_arn
  task_role_arn      = module.iam_roles.ecs_task_role_arn

  container_port   = 8080
  listener_port    = 80
  desired_count    = 1

  public_sg_id     = module.sg_group.public_sg_id

}

#################################
# 7. Módulo DOCUMENTDB
#################################
module "documentdb" {
  source            = "./modules/documentdb"
  name              = "${var.project_name}-docdb"
  vpc_id            = module.vpc.vpc_id
  public_subnets    = module.vpc.public_subnets

  public_sg_id      = module.sg_group.public_sg_id

  master_username   = "adminuser"
  master_password   = "AdminPassword123!"
  instance_class    = "db.t4g.medium"
}

