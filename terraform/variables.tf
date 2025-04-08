variable "aws_region" {
  type        = string
  description = "Región donde se desplegará la infraestructura"
  default     = "us-east-1"
}

variable "project_name" {
  type        = string
  description = "Nombre global para el proyecto (tagging y nombres de recursos)"
  default     = "poc-project"
}

variable "availability_zones" {
  type        = list(string)
  description = "Zonas de disponibilidad para la VPC"
  default     = ["us-east-1a","us-east-1b"]
}
