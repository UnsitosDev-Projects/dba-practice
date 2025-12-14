from fastapi import FastAPI
from app.config import lifespan, APP_NAME, APP_PORT
from app.controller.StudentController import router as student_router

app = FastAPI(
    title="dba-students",
    description="Microservicio de Estudiantes con FastAPI",
    version="1.0.0",
    lifespan=lifespan
)
app.include_router(student_router, prefix="/api/students", tags=["Estudiantes"])
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

if __name__ == "__main__":
    uvicorn.run(
        app,
        host="0.0.0.0",
        port=APP_PORT
    )
