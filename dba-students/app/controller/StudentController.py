from fastapi import APIRouter, HTTPException, status
from app.service.StudentService import StudentService
from app.model.StudentModel import Student
from typing import List

router = APIRouter()
service = StudentService()

@router.post(
    "/",
    response_model=Student,
    status_code=status.HTTP_201_CREATED,
    summary="Crear un nuevo estudiante",
    description="Crea un nuevo estudiante y lo almacena en Redis"
)
async def create_student(student: Student):
    try:
        result = service.create_student(student.model_dump())
        return result
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Error al crear estudiante: {str(e)}"
        )

@router.get(
    "/{student_id}",
    response_model=Student,
    summary="Obtener estudiante por ID",
    description="Recupera un estudiante específico de Redis usando su ID"
)
async def get_student(student_id: str):
    student = service.get_student(student_id)
    if not student:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Estudiante con ID {student_id} no encontrado"
        )
    return student

@router.put(
    "/{student_id}",
    response_model=Student,
    summary="Actualizar estudiante",
    description="Actualiza la información de un estudiante existente en Redis"
)
async def update_student(student_id: str, student: Student):
    student_data = student.model_dump()
    student_data.pop("id", None)
    
    result = service.update_student(student_id, student_data)
    if not result:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Estudiante con ID {student_id} no encontrado"
        )
    return result

@router.delete(
    "/{student_id}",
    status_code=status.HTTP_204_NO_CONTENT,
    summary="Eliminar estudiante",
    description="Elimina un estudiante de Redis"
)
async def delete_student(student_id: str):
    success = service.delete_student(student_id)
    if not success:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Estudiante con ID {student_id} no encontrado"
        )

@router.get(
    "/",
    response_model=List[Student],
    summary="Obtener todos los estudiantes",
    description="Recupera todos los estudiantes almacenados en Redis"
)
async def get_all_students():
    return service.get_all_students()