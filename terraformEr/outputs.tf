output "mongo_public_ip" {
  description = "Public IP of the MongoDB instance"
  value       = module.mongodb.public_ip
}

output "mongo_connection_string" {
  description = "MongoDB Connection URI"
  value       = "mongodb://${var.mongo_user}:${var.mongo_password}@${module.mongodb.public_ip}:${var.mongo_port}/${var.mongo_db}?authSource=admin"
}