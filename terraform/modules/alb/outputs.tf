output "alb_arn" {
  value       = aws_lb.this.arn
  description = "ARN del ALB"
}

output "alb_dns_name" {
  value       = aws_lb.this.dns_name
  description = "DNS del ALB"
}

output "alb_sg_id" {
  value       = aws_security_group.alb_sg.id
  description = "Security Group ID del ALB"
}
