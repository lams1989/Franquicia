output "ecs_cluster_name" {
  description = "ECS Cluster Name"
  value       = aws_ecs_cluster.franquicia_cluster.name
}

output "s3_bucket_name" {
  description = "S3 Bucket Name for Logs"
  value       = aws_s3_bucket.logs_bucket.bucket
}

output "docdb_endpoint" {
  description = "Amazon DocumentDB Cluster Endpoint"
  value       = aws_docdb_cluster.franquicia.endpoint
}