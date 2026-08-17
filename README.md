# Spring WebFlux + MongoDB (NoSQL) - Dual Cloud & Local

Proyecto desarrollado con **Spring WebFlux (Reactivo)** y **MongoDB** que conecta **simultáneamente** a **MongoDB Atlas (Cloud)** y **MongoDB Local (Docker)** con un **solo comando de ejecución**.

---

## 📌 Características Principales

✅ **Un solo comando de inicio**: La aplicación se conecta a ambas bases de datos al encender.  
✅ **Base de Datos Cloud**: `database_cloud` y colección `customer` en **MongoDB Atlas**.  
✅ **Base de Datos Local**: `database_local` y colección `customer` en **MongoDB Local (Docker)**.  
✅ **Imagen DockerHub**: Contenedor ejecutado desde `hugo454/mongodb:8`.  
✅ **Swagger UI Interactivo**: Pruebas visuales de endpoints locales, cloud y simultáneos.

---

## 🐳 Ejecutar Contenedor Docker Local

```bash
docker run -d --name mongodb -p 27017:27017 -v mongodb_data:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin12345 hugo454/mongodb:8
```

---

## ⚡ Ejecución del Proyecto (Un solo comando)

```bash
mvn spring-boot:run
```

---

## 🚀 Endpoints de la API

Base URL: `http://localhost:8081`  
Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

### 🍃 MongoDB Local (`database_local`)
- `GET /v1/api/customer/local`
- `GET /v1/api/customer/local/{id}`
- `POST /v1/api/customer/local/save`

### ☁️ MongoDB Cloud Atlas (`database_cloud`)
- `GET /v1/api/customer/cloud`
- `GET /v1/api/customer/cloud/{id}`
- `POST /v1/api/customer/cloud/save`

### ⚡ Simultáneos (Ambas BD a la vez)
- `GET /v1/api/customer` (Consulta combinada de Local + Cloud)
- `POST /v1/api/customer/save` (Guarda el cliente en ambas bases de datos simultáneamente)
