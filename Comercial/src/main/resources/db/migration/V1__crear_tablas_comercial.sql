CREATE TABLE productos (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           categoria VARCHAR(100) NOT NULL,
                           precio INT NOT NULL,
                           stock INT NOT NULL,
                           estado_stock VARCHAR(50)
);

CREATE TABLE ventas (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        socio_id BIGINT NOT NULL,
                        producto_id BIGINT NOT NULL,
                        cantidad INT NOT NULL,
                        precio_unitario INT NOT NULL,
                        total INT NOT NULL,
                        fecha_venta DATETIME NOT NULL
);