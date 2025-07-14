# Prueba Técnica Amaris – Backend Serverless con Java y AWS

Este proyecto implementa un backend en **Spring Boot (Java 17)** desplegado sobre **AWS Lambda** usando **Serverless Framework**. Utiliza **DynamoDB** como base de datos y expone documentación automática mediante **Swagger UI**.

---

## 🚀 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes componentes:

### 1. Java 17+
- Verifica con: `java -version`

### 2. Maven
- Verifica con: `mvn -version`

### 3. Node.js y npm
- Node.js v16 o superior
- Verifica con: `node -v` y `npm -v`

### 4. Serverless Framework
- Instala globalmente:
  ```bash
  npm install -g serverless
  ```
- Verifica con: `serverless -v`

### 5. Cuenta de AWS y credenciales
Debes contar con un usuario de AWS con permisos para:
- Lambda
- API Gateway
- DynamoDB
- CloudFormation
- IAM

---

## 📦 Clonar el Repositorio

Puedes clonar manualmente:

```bash
git clone https://github.com/itachi364/pruebaamaris
cd pruebaamaris
```

O ejecutar el script automatizado (ver sección siguiente).

---

## ⚙️ Configuración y Despliegue Automático

Este proyecto incluye dos scripts para automatizar la configuración y el despliegue:

### Para usuarios **Windows**:

Ejecuta:

```bash
deploy.bat
```

### Para usuarios **Linux / Mac**:

Ejecuta:

```bash
chmod +x deploy.sh
./deploy.sh
```

### ¿Qué hacen estos scripts?
1. Clonan el repositorio (si no existe).
2. Solicitan al usuario:
   - El **perfil de AWS** a usar (`--aws-profile`)
   - Si desea crear las tablas en DynamoDB (`--param=createTables=true/false`)
3. Ejecutan:
   - `mvn clean package -DskipTests`
   - `serverless deploy`

---

## 📄 Acceso a Swagger UI

Una vez desplegado correctamente, accede a la documentación Swagger en la URL:

```
https://<API_ID>.execute-api.<REGION>.amazonaws.com/dev/swagger-ui.html
```

Ejemplo:

```
https://a2hfg1f856.execute-api.us-east-1.amazonaws.com/dev/swagger-ui.html
```

---

## ✅ Notas

- La base de datos DynamoDB puede ser creada o reutilizada según el parámetro `createTables`.
- Swagger utiliza `springdoc-openapi` para autogenerar la documentación.
- El backend está diseñado para ejecutarse sobre Lambda con API Gateway de tipo HTTP.

---

## 👨‍💻 Desarrollado por

**Michael Vanegas**  
Backend Senior Developer  
