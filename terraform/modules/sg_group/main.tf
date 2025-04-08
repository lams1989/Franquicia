resource "aws_security_group" "public_sg" {
  name        = "${var.project_name}-public-sg"
  description = "Single SG for ECS and DocumentDB in public subnets"
  vpc_id      = var.vpc_id

  # 1. Reglas Inbound para el Microservicio (ECS Fargate)
  #    Permite que clientes de Internet accedan a tu app en el puerto 8080 (o el que uses)
  ingress {
    description = "Allow HTTP from anywhere"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # 2. Regla Inbound para DocumentDB
  #    Permite que el puerto 27017 reciba tráfico solo desde el mismo SG
  #    (Self-referencing rule). Esto significa que solo los recursos que usen este SG
  #    podrán acceder a 27017.
  ingress {
    description     = "Allow DocumentDB traffic from self (ECS tasks share the same SG)"
    from_port       = 27017
    to_port         = 27017
    protocol        = "tcp"
    self            = true
  }

  # 3. Regla Egress: permitir todo el tráfico saliente
  egress {
    description = "Allow all outbound"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-public-sg"
  }
}