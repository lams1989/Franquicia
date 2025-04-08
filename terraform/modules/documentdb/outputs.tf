output "docdb_endpoint" {
  description = "Endpoint de DocumentDB"
  value       = aws_docdb_cluster.this.endpoint
}

output "docdb_port" {
  description = "Puerto de DocumentDB"
  value       = aws_docdb_cluster.this.port
}

output "docdb_sg_id" {
  description = "Security Group ID de DocumentDB"
  value       = aws_security_group.docdb_sg.id
}
