resource "aws_key_pair" "franquicia_key" {
  key_name   = var.key_name
  public_key = var.public_key
}

output "key_name" {
  value = aws_key_pair.franquicia_key.key_name
}