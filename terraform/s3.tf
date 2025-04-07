resource "aws_s3_bucket" "logs_bucket" {
  bucket = var.s3_bucket_name
  acl    = "private"

  versioning {
    enabled = true
  }

  lifecycle {
    prevent_destroy = true
  }

  tags = {
    Name        = "Franquicia Logs"
    Environment = "Production"
  }
}
