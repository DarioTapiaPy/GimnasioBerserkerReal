CREATE TABLE facturas (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          id_socio BIGINT NOT NULL,
                          valor DOUBLE NOT NULL,
                          fecha_facturacion DATETIME NOT NULL
);

CREATE TABLE pagos (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       factura_id BIGINT NOT NULL,
                       fecha_pago DATE NOT NULL,
                       monto_pago DOUBLE NOT NULL,
                       metodo_pago VARCHAR(50) NOT NULL
);