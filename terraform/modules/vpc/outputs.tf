output "vpc_id" {
  description = "ID de la VPC creada"
  value       = aws_vpc.this.id
}

output "public_subnets" {
  description = "Lista de IDs de las subnets públicas"
  value       = [for pub in aws_subnet.public_subnets : pub.id]
}
