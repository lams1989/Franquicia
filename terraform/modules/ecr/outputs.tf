output "repo_url" {
  value       = aws_ecr_repository.this.repository_url
  description = "URL del repositorio ECR"
}
