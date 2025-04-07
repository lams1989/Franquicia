variable "aws_region" {
  description = "Región de AWS"
  default     = "us-east-1"
}

variable "key_name" {
  description = "Nombre de la clave SSH"
  default     = "franquicia-key"
}

variable "mongo_port" {
  description = "Puerto de MongoDB"
  default     = 27017
}

variable "ami_id" {
  description = "AMI de Amazon Linux 2"
  default     = "ami-0c55b159cbfafe1f0"
}

variable "instance_type" {
  description = "Tipo de instancia de EC2"
  default     = "t2.micro"
}

variable "mongo_user" {
  description = "Usuario de MongoDB"
  default     = "franquicia_user"
}

variable "mongo_password" {
  description = "Contraseña de MongoDB"
  default     = "franquicia123"
}

variable "mongo_db" {
  description = "Nombre de la base de datos"
  default     = "franquicia_db"
}