resource "aws_ecs_cluster" "franquicia_cluster" {
  name = var.ecs_cluster_name
}

resource "aws_ecs_task_definition" "franquicia_task" {
  family                   = "franquicia-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"

  container_definitions = jsonencode([
    {
      name      = "franquicia-container"
      image     = "myrepo/franquicia-api:latest"
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
      environment = [
        {
          name  = "MONGO_URI"
          value = aws_docdb_cluster.franquicia.endpoint
        }
      ]
    }
  ])

  execution_role_arn = aws_iam_role.ecsTaskExecutionRole.arn
}

resource "aws_ecs_service" "franquicia_service" {
  name            = "franquicia-service"
  cluster         = aws_ecs_cluster.franquicia_cluster.id
  task_definition = aws_ecs_task_definition.franquicia_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = ["subnet-12345", "subnet-67890"]
    security_groups = ["sg-12345678"]
    assign_public_ip = true
  }
}
