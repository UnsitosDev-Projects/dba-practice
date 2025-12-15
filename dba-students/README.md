# dba-students - Joan

Microservicio de Estudiantes con Python FastAPI integrado con Spring Cloud (Eureka).

## Instalación

```bash
# Crear entorno virtual
python -m venv venv

# Activar entorno virtual
# Linux/Mac:
source venv/bin/activate
# Windows:
venv\Scripts\activate

# Instalar dependencias
pip install -r requirements.txt
```

## Ejecución

**IMPORTANTE:** Debes usar el puerto 8001 para que el gateway funcione correctamente.

```bash
# Producción (recomendado)
uvicorn app.main:app --host 0.0.0.0 --port 8001

# Desarrollo con recarga automática
uvicorn app.main:app --reload --host 0.0.0.0 --port 8001
```

**Nota:** NO uses `fastapi dev` ya que usa el puerto 8000 por defecto.

## Endpoints

### Endpoints del Servicio (Puerto 8001)

- `GET /` - Root endpoint
- `GET /health` - Health check
- `GET /docs` - Documentación Swagger automática de FastAPI

### Endpoints a través del Gateway (Puerto 8080)

#### **GET** `/api/students/` - Obtener todos los estudiantes
```bash
curl http://localhost:8080/api/students/
```

#### **POST** `/api/students/` - Crear un nuevo estudiante
```bash
curl -X POST http://localhost:8080/api/students/ \
  -H "Content-Type: application/json" \
  -d '{"name": "Juan Pérez", "email": "juan@example.com", "career": "Ingeniería en Sistemas"}'
```

#### **GET** `/api/students/{student_id}` - Obtener estudiante por ID
```bash
curl http://localhost:8080/api/students/1
```

#### **PUT** `/api/students/{student_id}` - Actualizar estudiante
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"id": "1", "name": "Juan Updated", "email": "juan@example.com", "career": "Ingeniería en Sistemas"}'
```

#### **DELETE** `/api/students/{student_id}` - Eliminar estudiante
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

### Modelo de Datos

```json
{
  "id": "string (UUID generado automáticamente)",
  "name": "string (requerido)",
  "email": "string (email válido, requerido)",
  "career": "string (requerido)"
}
```
