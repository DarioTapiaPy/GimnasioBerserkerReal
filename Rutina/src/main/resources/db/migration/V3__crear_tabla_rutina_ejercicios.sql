CREATE TABLE rutina_ejercicios (
                                   rutina_id BIGINT NOT NULL,
                                   ejercicios_id BIGINT NOT NULL,

                                   PRIMARY KEY (rutina_id, ejercicios_id),

                                   CONSTRAINT fk_rutina
                                       FOREIGN KEY (rutina_id)
                                           REFERENCES rutina(id),

                                   CONSTRAINT fk_ejercicio
                                       FOREIGN KEY (ejercicios_id)
                                           REFERENCES ejercicio(id)
);