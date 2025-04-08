output "alb_dns_name" {
  description = "DNS del ALB"
  value       = module.alb.alb_dns_name
}

output "docdb_endpoint" {
  description = "Endpoint de DocumentDB"
  value       = module.documentdb.docdb_endpoint
}
