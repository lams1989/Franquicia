output "ecs_cluster_id" {
  value       = aws_ecs_cluster.this.id
  description = "ID del ECS Cluster"
}

output "ecs_cluster_arn" {
  value       = aws_ecs_cluster.this.arn
  description = "ARN del ECS Cluster"
}
