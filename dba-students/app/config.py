import os
from contextlib import asynccontextmanager
import py_eureka_client.eureka_client as eureka_client

# Configuración desde variables de entorno o valores por defecto
APP_NAME = os.getenv("APP_NAME", "dba-students")
APP_PORT = int(os.getenv("APP_PORT", "8001"))
INSTANCE_HOST = os.getenv("INSTANCE_HOST", "localhost")
EUREKA_SERVER = os.getenv("EUREKA_SERVER", "http://localhost:8761/eureka")
ENABLE_EUREKA = os.getenv("ENABLE_EUREKA", "false").lower() == "true"

@asynccontextmanager
async def lifespan(app):
    # Startup: Registrar en Eureka solo si está habilitado
    if ENABLE_EUREKA:
        try:
            await eureka_client.init_async(
                eureka_server=EUREKA_SERVER,
                app_name=APP_NAME,
                instance_port=APP_PORT,
                instance_host=INSTANCE_HOST
            )
            print(f"Microservicio {APP_NAME} registrado en Eureka")
        except Exception as e:
            print(f"Error al conectar con Eureka: {e}")
    else:
        print(f"Microservicio {APP_NAME} iniciado sin Eureka")
    
    yield
    
    # Shutdown: Des-registrar de Eureka
    if ENABLE_EUREKA:
        try:
            await eureka_client.stop()
            print(f"Microservicio {APP_NAME} des-registrado de Eureka")
        except Exception as e:
            print(f"Error al des-registrar de Eureka: {e}")
