variable "ami_id" {
  description = "AMI de la instancia EC2"
  type        = string
}

variable "instance_type" {
  description = "Tipo de instancia de EC2"
  type        = string
}

variable "key_name" {
  description = "Nombre de la clave SSH"
  type        = string
}

variable "security_group" {
  description = "ID del grupo de seguridad para la instancia EC2"
  type        = string
}

variable "mongo_user" {
  description = "Usuario para la base de datos MongoDB"
  type        = string
}

variable "mongo_password" {
  description = "Contraseña para la base de datos MongoDB"
  type        = string
}

variable "mongo_db" {
  description = "Nombre de la base de datos MongoDB"
  type        = string
}

variable "mongo_port" {
  description = "Puerto de MongoDB"
  type        = number
  default     = 27017
}