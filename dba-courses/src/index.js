const express = require('express');
require('dotenv').config();
const { startEureka, stopEureka } = require('./config');

const app = express();
const PORT = process.env.PORT || 8002;

app.use(express.json());

// Rutas
app.get('/', (req, res) => {
  res.json({
    message: 'Microservicio de Cursos funcionando',
    service: 'dba-courses',
    status: 'UP'
  });
});

app.get('/health', (req, res) => {
  res.json({
    status: 'UP',
    service: 'dba-courses'
  });
});

app.get('/api/courses', (req, res) => {
  res.json({
    message: 'Lista de cursos',
    courses: [
      { id: 1, name: 'Base de Datos Avanzadas', credits: 8 },
      { id: 2, name: 'Microservicios', credits: 6 },
      { id: 3, name: 'Cloud Computing', credits: 7 }
    ]
  });
});

app.get('/api/courses/:id', (req, res) => {
  const { id } = req.params;
  res.json({
    id: parseInt(id),
    name: `Curso ${id}`,
    credits: 6,
    description: `Descripción del curso ${id}`
  });
});

// Iniciar servidor
app.listen(PORT, () => {
  console.log(`Servidor corriendo en puerto http://localhost:${PORT}`);
  startEureka();
});

// Manejo de cierre graceful
process.on('SIGINT', () => {
  console.log('Cerrando servidor...');
  stopEureka();
  process.exit(0);
});
