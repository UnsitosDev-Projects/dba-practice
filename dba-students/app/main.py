from fastapi import FastAPI
from app.config import lifespan, APP_NAME, APP_PORT

app = FastAPI(
    title="dba-students",
    description="Microservicio de Estudiantes con FastAPI",
    version="1.0.0",
    lifespan=lifespan
)

@app.get("/")
async def root():
    return {
        "message": "Microservicio de Estudiantes funcionando",
        "service": "dba-students",
        "status": "UP"
    }

@app.get("/health")
async def health():
    return {
        "status": "UP",
        "service": "dba-students"
    }

@app.get("/api/students")
async def get_students():
    return {
        "message": "Lista de estudiantes",
        "students": [
            {"id": 1, "name": "Juan Pérez", "email": "juan@example.com"},
            {"id": 2, "name": "María García", "email": "maria@example.com"}
        ]
    }

@app.get("/api/students/{student_id}")
async def get_student(student_id: int):
    return {
        "id": student_id,
        "name": f"Estudiante {student_id}",
        "email": f"estudiante{student_id}@example.com"
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=APP_PORT)
