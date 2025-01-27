CREATE TABLE IF NOT EXISTS tarea (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    estado VARCHAR(255) NOT NULL CHECK (estado IN ('PENDIENTE', 'COMPLETADA')),
    fecha_creacion TIMESTAMP
    );
