from app.redis.RedisConfig import RedisConnection
from app.model.StudentModel import Student
import json
from typing import Optional, List

class StudentRepository:
    def __init__(self):
        self.redis = RedisConnection.get_connection()
        self.key_prefix = "student:"
    
    def _get_key(self, student_id: str) -> str:
        return f"{self.key_prefix}{student_id}"
    
    def save(self, student: Student) -> Student:
        key = self._get_key(student.id)
        student_dict = student.model_dump()
        self.redis.hset(key, mapping=student_dict)
        return student
    
    def find_by_id(self, student_id: str) -> Optional[Student]:
        key = self._get_key(student_id)
        student_data = self.redis.hgetall(key)
        if not student_data:
            return None
        return Student(**student_data)
    
    def update(self, student_id: str, student: Student) -> Optional[Student]:
        key = self._get_key(student_id)
        if not self.redis.exists(key):
            return None
        student_dict = student.model_dump()
        self.redis.hset(key, mapping=student_dict)
        return student
    
    def delete(self, student_id: str) -> bool:
        key = self._get_key(student_id)
        if not self.redis.exists(key):
            return False
        deleted = self.redis.delete(key)
        return deleted > 0
    
    def find_all(self) -> List[Student]:
        students = []
        pattern = f"{self.key_prefix}*"
        keys = self.redis.keys(pattern)
        for key in keys:
            student_data = self.redis.hgetall(key)
            if student_data:
                students.append(Student(**student_data))
        return students