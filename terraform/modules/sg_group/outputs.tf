output "public_sg_id" {
  description = "ID de la VPC creada"
  value       = aws_security_group.this.id
}
