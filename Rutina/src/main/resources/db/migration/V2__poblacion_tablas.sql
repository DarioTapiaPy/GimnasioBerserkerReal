INSERT INTO rutina (nombre, objetivo, duracion_semanas)
VALUES
    ('Full Body', 'Acondicionamiento general y ganancia muscular', 8),
    ('Piernas', 'Desarrollo de piernas y gluteos', 10),
    ('Pecho y Triceps', 'Hipertrofia de torso superior', 8),
    ('Espalda y Biceps', 'Fortalecimiento de espalda y brazos', 8);


-- 1. FULL BODY
-- Manteniendo un balance real: 1 de piernas, 1 de pecho, 1 de espalda, 1 de hombros
INSERT INTO ejercicio (nombre, series, repeticiones, grupo_muscular, rutina_id)
VALUES
    ('Sentadilla con Barra', 4, 10, 'Piernas', 1),
    ('Press de Banca', 4, 10, 'Pecho', 1),
    ('Remo con Barra', 4, 10, 'Espalda', 1),
    ('Press Militar', 3, 12, 'Hombros', 1);


-- 2. PIERNAS
-- Reemplazamos las zancadas genéricas y movemos el peso muerto (versión rumano) a su lugar correcto
INSERT INTO ejercicio (nombre, series, repeticiones, grupo_muscular, rutina_id)
VALUES
    ('Prensa Inclinada', 4, 12, 'Piernas', 2),
    ('Peso Muerto Rumano', 4, 10, 'Isquiotibiales', 2),
    ('Hip Thrust', 4, 10, 'Gluteos', 2),
    ('Extension de Cuadriceps', 3, 15, 'Cuadriceps', 2),
    ('Elevacion de Talones en Maquina', 4, 15, 'Pantorrillas', 2);


-- 3. PECHO Y TRICEPS
-- Agregamos un trabajo de poleas para mayor variedad en el pecho
INSERT INTO ejercicio (nombre, series, repeticiones, grupo_muscular, rutina_id)
VALUES
    ('Press Banca Plano', 4, 10, 'Pecho', 3),
    ('Press Inclinado con Mancuernas', 4, 12, 'Pecho', 3),
    ('Cruce de Poleas', 3, 15, 'Pecho', 3),
    ('Fondos en Paralelas', 3, 12, 'Triceps', 3),
    ('Extension de Triceps en Polea', 3, 15, 'Triceps', 3);


-- 4. ESPALDA Y BICEPS
-- Agregamos un pulldown para aislar bien la espalda sin usar el bíceps
INSERT INTO ejercicio (nombre, series, repeticiones, grupo_muscular, rutina_id)
VALUES
    ('Dominadas', 4, 8, 'Espalda', 4),
    ('Remo en Polea Baja', 4, 10, 'Espalda', 4),
    ('Pullover en Polea Alta', 3, 12, 'Espalda', 4),
    ('Curl con Barra', 3, 12, 'Biceps', 4),
    ('Curl Martillo con Mancuernas', 3, 12, 'Biceps', 4);