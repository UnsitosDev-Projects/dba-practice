import redis
from pydantic_settings import BaseSettings
import os
from dotenv import load_dotenv

load_dotenv()

class Settings(BaseSettings):
    redis_host: str = os.getenv("REDIS_HOST", "localhost")
    redis_port: int = int(os.getenv("REDIS_PORT", 6379))
    redis_db: int = int(os.getenv("REDIS_DB", 0))
    
    class Config:
        env_file = ".env"

settings = Settings()

class RedisConnection:
    _instance = None
    
    @classmethod
    def get_connection(cls):
        if cls._instance is None:
            cls._instance = redis.Redis(
                host=settings.redis_host,
                port=settings.redis_port,
                db=settings.redis_db,
                decode_responses=True
            )
            # Test connection
            try:
                cls._instance.ping()
                print("✅ Conexión a Redis establecida")
            except redis.ConnectionError:
                print("❌ No se pudo conectar a Redis")
                raise
        return cls._instance