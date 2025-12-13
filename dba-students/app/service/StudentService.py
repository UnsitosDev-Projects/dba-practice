from app.repository.StudentRepository import StudentRepository
from app.model.StudentModel import Student
from typing import Optional, List
import uuid

class StudentService:
    def __init__(self):
        self.repository = StudentRepository()
    
    def create_student(self, student_data: dict) -> Student:
        """Crea un nuevo estudiante"""
        if "id" not in student_data or not student_data["id"]:
            student_data["id"] = str(uuid.uuid4())
        
        student = Student(**student_data)
        return self.repository.save(student)
    
    def get_student(self, student_id: str) -> Optional[Student]:
        """Obtiene un estudiante por ID"""
        return self.repository.find_by_id(student_id)
    
    def update_student(self, student_id: str, student_data: dict) -> Optional[Student]:
        """Actualiza un estudiante existente"""
        existing = self.repository.find_by_id(student_id)
        if not existing:
            return None
        updated_data = existing.model_dump()
        updated_data.update(student_data)
        updated_data["id"] = student_id
        
        student = Student(**updated_data)
        return self.repository.update(student_id, student)
    
    def delete_student(self, student_id: str) -> bool:
        """Elimina un estudiante"""
        return self.repository.delete(student_id)
    
    def get_all_students(self) -> List[Student]:
        """Obtiene todos los estudiantes"""
        return self.repository.find_all()