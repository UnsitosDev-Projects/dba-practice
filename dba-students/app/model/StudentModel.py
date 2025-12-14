from pydantic import BaseModel, Field, EmailStr
from typing import Optional
import uuid

class Student(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    email: EmailStr
    career: str