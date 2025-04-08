resource "aws_security_group" "docdb_sg" {
  name        = "${var.name}-docdb-sg"
  description = "Security Group for DocumentDB"
  vpc_id      = var.vpc_id

  ingress {
    description     = "Allow ECS tasks to connect on 27017"
    from_port       = 27017
    to_port         = 27017
    protocol        = "tcp"
    security_groups = [var.public_sg_id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_docdb_subnet_group" "this" {
  name       = "${var.name}-docdb-subnet-group"
  subnet_ids = var.public_subnets
  tags = {
    Name = "${var.name}-docdb-subnet-group"
  }
}

resource "aws_docdb_cluster" "this" {
  cluster_identifier      = "${var.name}-docdb"
  engine_version          = "5.0.0"
  master_username         = var.master_username
  master_password         = var.master_password
  db_subnet_group_name    = aws_docdb_subnet_group.this.name

  vpc_security_group_ids  = [var.public_sg_id]
  deletion_protection     = false
  apply_immediately       = true

  # Ajusta retención si deseas backups
  backup_retention_period = 1

  tags = {
    Name = "${var.name}-docdb-cluster"
  }
}

resource "aws_docdb_cluster_instance" "primary" {
  identifier         = "${var.name}-docdb-instance"
  cluster_identifier = aws_docdb_cluster.this.id
  instance_class     = var.instance_class
  apply_immediately  = true

  tags = {
    Name = "${var.name}-docdb-instance"
  }
}
