variable "name" {
  type        = string
  description = "Nombre para el ALB"
}

variable "vpc_id" {
  type        = string
  description = "ID de la VPC"
}

variable "public_subnets" {
  type        = list(string)
  description = "Subnets públicas para el ALB"
}
