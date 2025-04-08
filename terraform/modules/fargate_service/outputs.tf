output "ecs_service_name" {
  description = "Nombre del servicio ECS"
  value       = aws_ecs_service.this.name
}

output "ecs_task_definition_arn" {
  description = "ARN de la Task Definition de ECS"
  value       = aws_ecs_task_definition.this.arn
}

output "target_group_arn" {
  description = "ARN del Target Group"
  value       = aws_lb_target_group.this.arn
}
