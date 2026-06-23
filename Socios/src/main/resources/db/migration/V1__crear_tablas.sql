CREATE TABLE Membresias (
                            id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                            tipo            VARCHAR(11)  NOT NULL UNIQUE,
                            precio          INT          NOT NULL,
                            duracion_meses  INT          NOT NULL
);

CREATE TABLE Socios (
                        id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                        rut              VARCHAR(11)  NOT NULL UNIQUE,
                        nombre           VARCHAR(100) NOT NULL,
                        email            VARCHAR(100) NOT NULL UNIQUE,
                        estado_membresia BOOLEAN      NOT NULL DEFAULT TRUE,
                        plan_id          BIGINT       NOT NULL,
                        rutina_id        BIGINT,
                        CONSTRAINT fk_socio_membresia
                            FOREIGN KEY (plan_id) REFERENCES Membresias(id)
);