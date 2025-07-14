@echo off
echo === Clonando el repositorio ===
git clone https://github.com/itachi364/pruebaamaris.git
cd pruebaamaris

echo === Configurando AWS CLI ===
aws configure

set /p AWS_PROFILE=Ingrese el nombre del perfil AWS: 
set /p CREATE_TABLES=¿Desea crear las tablas? (true/false): 

echo === Compilando el proyecto sin tests ===
mvn clean package -DskipTests

echo === Desplegando con Serverless ===
serverless deploy --aws-profile %AWS_PROFILE% --param=createTables=%CREATE_TABLES%
pause
