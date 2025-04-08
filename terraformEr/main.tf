provider "aws" {
  region = var.aws_region
}

module "key_pair" {
  source     = "./modules/key_pair"
  key_name   = var.key_name
  public_key = file("${path.module}/id_rsa.pub")
}

module "security_group" {
  source     = "./modules/security_group"
  mongo_port = var.mongo_port
}

module "mongodb" {
  source          = "./modules/mongodb"
  ami_id          = var.ami_id
  instance_type   = var.instance_type
  key_name        = module.key_pair.key_name
  security_group  = module.security_group.security_group_id
  mongo_user      = var.mongo_user
  mongo_password  = var.mongo_password
  mongo_db        = var.mongo_db
  mongo_port      = var.mongo_port
}