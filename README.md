# DBA Practice - Microservicios

## Gestores
- **Redis**. Joan Manuel Abeldaño
- **Amazon DynamoDB**. Manuel Alejandro Pinacho Hernández
- **Memcached**. Eleazar Jarquín Ramos
- **Riak**. Hugo Nicolás Aragón Maya
- **Etcd**. Daniel Bernardino Reyes Nolasco

**Cada gestor es un microserivicio diferente**

Proyecto de práctica basado en arquitectura de microservicios utilizando Spring Boot y Netflix Eureka como servidor de descubrimiento de servicios.

> [!IMPORTANT]
> **Para los compañeros del equipo:** Simplemente clonen este repositorio y desarrollen su microservicio aquí. **NO configuren nada relacionado con Eureka, Gateway o configuraciones de integración.** Yo me encargaré de toda la configuración necesaria para que su microservicio se integre correctamente a la aplicación.

> [!WARNING]
> **Flujo de trabajo con Git Flow:** Este proyecto utiliza Git Flow como estrategia de ramificación. **Todos los desarrollos deben realizarse en la rama `dev`**. NO trabajen directamente en `main`. Creen sus ramas de feature desde `dev` y hagan merge de regreso a `dev`.

## Descripción

Esta es una aplicación distribuida que implementa el patrón de arquitectura de microservicios. Los microservicios son un enfoque arquitectónico donde una aplicación se construye como un conjunto de servicios pequeños e independientes que se comunican entre sí a través de APIs.

> [!NOTE]
> Este proyecto utiliza **Netflix Eureka** como servidor de registro y descubrimiento de servicios, permitiendo que los microservicios se localicen y comuniquen entre sí de forma dinámica.

## Arquitectura

El proyecto está compuesto por los siguientes módulos:

| Módulo | Descripción | Puerto |
|--------|-------------|--------|
| `dba-eureka` | Servidor de descubrimiento de servicios (Eureka Server) | 8761 |
| `dba-gateway` | API Gateway para enrutamiento de peticiones | 8080 |

> [!IMPORTANT]
> El servidor Eureka (`dba-eureka`) debe iniciarse primero antes que los demás servicios para que puedan registrarse correctamente.

## Inicio Rápido 

```bash
# 1. Clonar el repositorio
git clone https://github.com/UnsitosDev-Projects/dba-practice.git
cd dba-practice

# 2. Compilar proyecto Java (en directorio raíz)
mvn clean install

# 3. Iniciar Eureka (Terminal 1)
cd dba-eureka && mvn spring-boot:run

# 4. Iniciar Gateway (Terminal 2)
cd dba-gateway && mvn spring-boot:run

# 5. Iniciar Students (Terminal 3)
cd dba-students && python -m venv venv && source venv/bin/activate
pip install -r requirements.txt
uvicorn app.main:app --host 0.0.0.0 --port 8001

# 6. Iniciar Courses (Terminal 4)
cd dba-courses && npm install && npm start

# 7. Iniciar Professor (Terminal 5)
cd dba-professor && mvn spring-boot:run

# 8. Verificar en el navegador
# http://localhost:8761 (Eureka Dashboard)
# http://localhost:8080/api/students (Students via Gateway)
# http://localhost:8080/api/courses (Courses via Gateway)
```

## Requisitos Previos

Antes de clonar y ejecutar este proyecto, asegúrate de tener instalado:

- **Java JDK 21** o superior
- **Maven 3.6+**
- **Python 3.8+**
- **Node.js 18+**
- **Git**

> [!TIP]
> Puedes verificar las versiones instaladas con los siguientes comandos:
> ```bash
> java -version
> mvn -version
> python --version
> node --version
> npm --version
> git --version
> ```

## Clonar el Repositorio

Para obtener una copia local del proyecto, ejecuta:

```bash
git clone https://github.com/UnsitosDev-Projects/dba-practice.git
cd dba-practice
```

> [!NOTE]
> Si es tu primera vez clonando un repositorio, asegúrate de tener Git configurado con tu nombre y correo:
> ```bash
> git config --global user.name "Tu Nombre"
> git config --global user.email "tu@email.com"
> ```

## Compilación del Proyecto

Desde el directorio raíz del proyecto, compila todos los módulos:

```bash
mvn clean install
```

Este comando descargará todas las dependencias necesarias y compilará cada microservicio.

> [!WARNING]
> La primera compilación puede tardar varios minutos mientras Maven descarga las dependencias.

## Ejecución de los Servicios

### Orden de Inicio Recomendado

**IMPORTANTE:** Debes iniciar los servicios en este orden específico:

#### 1. Servidor Eureka (Servicio de descubrimiento) - Puerto 8761
```bash
cd dba-eureka
mvn spring-boot:run
```
Verifica que esté corriendo accediendo a: `http://localhost:8761`

#### 2. API Gateway - Puerto 8080
```bash
cd dba-gateway
mvn spring-boot:run
```

#### 3. Microservicio de Students (Python/FastAPI) - Puerto 8001
```bash
cd dba-students
source venv/bin/activate  # Linux/Mac
# o venv\Scripts\activate en Windows
uvicorn app.main:app --host 0.0.0.0 --port 8001
```

#### 4. Microservicio de Courses (Node.js/Express) - Puerto 8002
```bash
cd dba-courses
npm start
```

#### 5. Microservicio de Professor (Java/Spring Boot) - Puerto 9090
```bash
cd dba-professor
mvn spring-boot:run
```

> [!TIP]
> Ejecuta cada servicio en una terminal separada para monitorear los logs independientemente.

### Probar los Microservicios a través del Gateway

Una vez que todos los servicios estén corriendo, puedes acceder a ellos a través del Gateway:

```bash
# Students
curl http://localhost:8080/api/students
curl http://localhost:8080/api/students/1

# Courses
curl http://localhost:8080/api/courses
curl http://localhost:8080/api/courses/1

# Professors
curl http://localhost:8080/api/professors
curl http://localhost:8080/api/professors/1
```

O desde el navegador:
- http://localhost:8080/api/students
- http://localhost:8080/api/courses
- http://localhost:8080/api/professors

## Verificación de los Servicios

Una vez que todos los servicios estén en ejecución, puedes verificar su registro en el dashboard de Eureka:

```
http://localhost:8761
```

Deberías ver todos los servicios registrados en la sección "Instances currently registered with Eureka".

> [!IMPORTANT]
> Si un servicio no aparece registrado en Eureka, verifica los logs en busca de errores de conexión o configuración.

## Estructura del Proyecto

```
dba-practice/
├── pom.xml                 # POM padre con configuración compartida
├── dba-eureka/            # Servidor de descubrimiento (Puerto 8761)
│   ├── src/
│   └── pom.xml
├── dba-gateway/           # API Gateway (Puerto 8080)
│   ├── src/
│   └── pom.xml
├── dba-students/          # Microservicio Python/FastAPI (Puerto 8001)
│   ├── app/
│   ├── requirements.txt
│   └── README.md
├── dba-courses/           # Microservicio Node.js/Express (Puerto 8002)
│   ├── src/
│   ├── package.json
│   └── README.md
└── dba-professor/         # Microservicio Java/Spring Boot (Puerto 9090)
    ├── src/
    └── pom.xml
```

## Tecnologías Utilizadas

### Infraestructura
- **Spring Boot 3.3.5** - Framework principal para servicios Java
- **Spring Cloud Netflix Eureka** - Servidor de descubrimiento de servicios
- **Spring Cloud Gateway** - API Gateway
- **Maven** - Gestión de dependencias y construcción

### Microservicios
- **Python 3.x + FastAPI** - Microservicio de Students
- **Node.js 18+ + Express** - Microservicio de Courses
- **Java 21 + Spring Boot** - Microservicio de Professor
- **Memcached** - Sistema de caché para Professor service

## Solución de Problemas

### El servicio no se registra en Eureka

> [!WARNING]
> Verifica que el servidor Eureka esté corriendo antes de iniciar otros servicios.

### Error de puerto ya en uso

Si recibes un error indicando que el puerto ya está en uso, puedes:
- Detener el proceso que está usando el puerto
- Cambiar el puerto en el archivo `application.yml` del servicio correspondiente

### Errores de compilación

> [!TIP]
> Intenta limpiar y recompilar el proyecto:
> ```bash
> mvn clean install -U
> ```

## Contribuir

Si deseas contribuir a este proyecto:

1. Haz fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios y haz commit (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

> [!NOTE]
> Este es un proyecto educativo con fines de práctica y aprendizaje de arquitecturas de microservicios bases de datos.
