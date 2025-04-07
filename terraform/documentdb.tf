resource "aws_docdb_cluster" "franquicia" {
  cluster_identifier      = var.docdb_cluster_id
  engine                  = "docdb"
  master_username         = var.docdb_admin_user
  master_password         = var.docdb_admin_password
  backup_retention_period = 7

  vpc_security_group_ids = ["sg-12345678"]

  tags = {
    Name = "Franquicia-DocumentDB"
  }
}

resource "aws_docdb_cluster_instance" "franquicia_instance" {
  identifier        = "${var.docdb_cluster_id}-instance"
  cluster_identifier = aws_docdb_cluster.franquicia.id
  instance_class    = var.docdb_instance_class
  apply_immediately = true
}
