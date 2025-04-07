variable "aws_region" {
  description = "AWS region"
  default     = "us-east-1"
}

variable "ecs_cluster_name" {
  description = "Name of the ECS cluster"
  default     = "franquicia-cluster"
}

variable "s3_bucket_name" {
  description = "S3 bucket for logs"
  default     = "franquicia-logs-bucket"
}

variable "docdb_cluster_id" {
  description = "Amazon DocumentDB cluster ID"
  default     = "franquicia-docdb"
}

variable "docdb_instance_class" {
  description = "DocumentDB instance class"
  default     = "db.r5.large"
}

variable "docdb_admin_user" {
  description = "DocumentDB admin username"
  default     = "admin"
}

variable "docdb_admin_password" {
  description = "DocumentDB admin password"
  default     = "SuperSecurePassword123!"
}