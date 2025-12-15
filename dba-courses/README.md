# dba-courses - Manuel

Microservicio de Cursos desarrollado con Node.js, Express y Eureka Client.

## Descripción

Este microservicio gestiona la información de cursos y se integra con Spring Cloud Eureka para el registro y descubrimiento de servicios.

## Requisitos

- Node.js 18+
- npm o yarn

## Instalación

```bash
npm install
```

## Ejecución

### Modo Desarrollo (sin variables de entorno)
```bash
npm run dev
```

### Modo Producción
```bash
npm start
```

### Con variables de entorno personalizadas
```bash
APP_PORT=8002 ENABLE_EUREKA=true EUREKA_HOST=localhost EUREKA_PORT=8761 npm start
```

El servidor se ejecutará en el puerto **8002** por defecto.

## Endpoints Disponibles

### Health Check
- **GET** `/` - Endpoint raíz con información del servicio
- **GET** `/health` - Estado del servicio

### Cursos
- **GET** `/api/courses` - Obtener lista de todos los cursos
- **GET** `/api/courses/:id` - Obtener un curso específico por ID

## Variables de Entorno (Opcionales)

Todas las variables tienen valores por defecto, no es necesario configurar un archivo `.env`:

- `APP_PORT` - Puerto del servidor (default: 8002)
- `ENABLE_EUREKA` - Habilitar registro en Eureka Server (default: true)
- `EUREKA_HOST` - Host de Eureka Server (default: localhost)
- `EUREKA_PORT` - Puerto de Eureka Server (default: 8761)

**Nota:** No es necesario crear un archivo `.env`, todas las configuraciones tienen valores predeterminados que funcionan con la configuración estándar del proyecto.

## Docker

### Construir imagen
```bash
docker build -t dba-courses .
```

### Ejecutar contenedor
```bash
docker run -p 8002:8002 dba-courses
```

## Integración con Eureka

Para habilitar el registro automático en Eureka Server:

```bash
export ENABLE_EUREKA=true
npm start
```

## Estructura del Proyecto

```
dba-courses/
├── src/
│   ├── index.js      # Punto de entrada de la aplicación
│   └── config.js     # Configuración de Eureka
├── package.json
├── Dockerfile
└── README.md
```

## Tecnologías

- Express.js - Framework web
- eureka-js-client - Cliente de Eureka para Node.js
- dotenv - Gestión de variables de entorno
- nodemon - Auto-reload en desarrollo
