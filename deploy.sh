#!/bin/bash

echo "=== Clonando el repositorio ==="
git clone https://github.com/itachi364/pruebaamaris.git
cd pruebaamaris || exit

echo "=== Configurando AWS CLI ==="
aws configure

read -p "Ingrese el nombre del perfil AWS: " AWS_PROFILE
read -p "¿Desea crear las tablas? (true/false): " CREATE_TABLES

echo "=== Compilando el proyecto sin tests ==="
mvn clean package -DskipTests

echo "=== Desplegando con Serverless ==="
serverless deploy --aws-profile "$AWS_PROFILE" --param="createTables=$CREATE_TABLES"
