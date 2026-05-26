CREATE TABLE rutina (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255) NOT NULL,
                        objetivo VARCHAR(255),
                        duracion_semanas INT
);

CREATE TABLE ejercicio (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           series INT,
                           repeticiones INT,
                           grupo_muscular VARCHAR(255),

                           rutina_id BIGINT,

                           CONSTRAINT fk_ejercicio_rutina
                               FOREIGN KEY (rutina_id)
                                   REFERENCES rutina(id)
                                   ON DELETE CASCADE
);