variable "name" {
  type        = string
  description = "Nombre base para los recursos de DocumentDB"
}

variable "vpc_id" {
  type        = string
  description = "ID de la VPC"
}

variable "public_subnets" {
  type        = list(string)
  description = "Subnets públicas donde se alojará DocumentDB"
}

variable "master_username" {
  type        = string
  description = "Usuario maestro para DocumentDB"
  default     = "adminuser"
}

variable "master_password" {
  type        = string
  description = "Password para DocumentDB"
  default     = "AdminPassword123!"
  sensitive   = true
}

variable "instance_class" {
  type        = string
  description = "Clase de instancia para DocumentDB"
  default     = "db.t4g.medium"
}

variable "public_sg_id" {
  type        = string
  description = "ID del Security Group compartido para ECS y DocumentDB"
}