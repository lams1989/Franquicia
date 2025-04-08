variable "service_name" {
  type        = string
  description = "Nombre del servicio ECS Fargate"
}

variable "image_url" {
  type        = string
  description = "URL de la imagen de contenedor (ECR repository + tag)"
}

variable "cluster_id" {
  type        = string
  description = "ID del ECS Cluster"
}

variable "task_cpu" {
  type    = number
  default = 256
}

variable "task_memory" {
  type    = number
  default = 512
}

variable "desired_count" {
  type    = number
  default = 1
}

variable "container_port" {
  type    = number
  default = 80
}

variable "public_subnets" {
  type        = list(string)
  description = "Subnets públicas para las tareas Fargate"
}

variable "vpc_id" {
  type        = string
  description = "VPC ID"
}

variable "alb_arn" {
  type        = string
  description = "ARN del ALB"
}

variable "alb_sg_id" {
  type        = string
  description = "Security Group del ALB"
}

variable "listener_port" {
  type    = number
  default = 80
}

variable "health_check_path" {
  type    = string
  default = "/"
}

variable "execution_role_arn" {
  type        = string
  description = "ARN del rol de ejecución (para pull de imagen, etc.)"
  default     = ""
}

variable "task_role_arn" {
  type        = string
  description = "ARN del rol de la tarea"
  default     = ""
}

variable "public_sg_id" {
  type        = string
  description = "Id grupo de seguridad publico"
}
