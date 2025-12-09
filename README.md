# DBA Practice - Microservicios

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
| `dba-config` | Servidor de configuración centralizada | 8888 |

> [!IMPORTANT]
> El servidor Eureka (`dba-eureka`) debe iniciarse primero antes que los demás servicios para que puedan registrarse correctamente.

## Requisitos Previos

Antes de clonar y ejecutar este proyecto, asegúrate de tener instalado:

- **Java JDK 17** o superior
- **Maven 3.6+**
- **Git**

> [!TIP]
> Puedes verificar las versiones instaladas con los siguientes comandos:
> ```bash
> java -version
> mvn -version
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

1. **Servidor Eureka** (Servicio de descubrimiento)
   ```bash
   cd dba-eureka
   mvn spring-boot:run
   ```
   
   Verifica que esté corriendo accediendo a: `http://localhost:8761`

2. **Servidor de Configuración**
   ```bash
   cd dba-config
   mvn spring-boot:run
   ```

3. **API Gateway**
   ```bash
   cd dba-gateway
   mvn spring-boot:run
   ```

> [!TIP]
> Puedes ejecutar cada servicio en terminales separadas para monitorear los logs de forma independiente.

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
├── dba-eureka/            # Servidor de descubrimiento
│   ├── src/
│   └── pom.xml
├── dba-gateway/           # API Gateway
│   ├── src/
│   └── pom.xml
└── dba-config/            # Servidor de configuración
    ├── src/
    └── pom.xml
```

## Tecnologías Utilizadas

- **Spring Boot** - Framework principal
- **Spring Cloud Netflix Eureka** - Servidor de descubrimiento de servicios
- **Spring Cloud Gateway** - API Gateway
- **Spring Cloud Config** - Configuración centralizada
- **Maven** - Gestión de dependencias y construcción

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
