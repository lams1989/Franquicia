variable "name" {
  type        = string
  description = "Nombre base para la VPC y recursos"
}

variable "vpc_cidr_block" {
  type        = string
  description = "CIDR Block para la VPC"
  default     = "10.0.0.0/16"
}

variable "azs" {
  type        = list(string)
  description = "Lista de zonas de disponibilidad para las subnets públicas"
  default     = ["us-east-2a","us-east-2b"]
}
