resource "aws_ecr_repository" "this" {
  name                 = var.repo_name
  image_tag_mutability = "MUTABLE"
  tags = {
    Name = var.repo_name
  }
}
