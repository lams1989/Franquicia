resource "aws_instance" "mongodb" {
  ami                         = var.ami_id
  instance_type               = var.instance_type
  key_name                    = var.key_name
  security_groups             = [var.security_group]
  associate_public_ip_address = true

  user_data = <<-EOF
    #!/bin/bash
    yum update -y
    amazon-linux-extras enable epel
    yum install -y mongodb-org
    systemctl start mongod
    systemctl enable mongod

    # Configuración de MongoDB
    mongo --eval 'db.createUser({user: "${var.mongo_user}", pwd: "${var.mongo_password}", roles: [{role: "readWrite", db: "${var.mongo_db}"}]})' admin
    sed -i 's/^#bindIp: 127.0.0.1/bindIp: 0.0.0.0/' /etc/mongod.conf
    systemctl restart mongod
  EOF

  tags = {
    Name = "MongoDB-Instance"
  }
}

output "public_ip" {
  description = "Dirección IP pública de la instancia MongoDB"
  value       = aws_instance.mongodb.public_ip
}