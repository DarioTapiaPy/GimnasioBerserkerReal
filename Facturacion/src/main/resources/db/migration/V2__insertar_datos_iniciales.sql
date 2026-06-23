INSERT INTO facturas (id_socio, valor, fecha_facturacion)
VALUES
    (1, 28000, '2026-05-01 10:00:00'),
    (2, 25000, '2026-05-05 11:30:00'),
    (3, 22500, '2026-05-10 15:45:00');

INSERT INTO pagos (factura_id, fecha_pago, monto_pago, metodo_pago)
VALUES
    (1, '2026-05-02', 28000, 'DEBITO'),
    (2, '2026-05-06', 25000, 'CREDITO'),
    (3, '2026-05-11', 22500, 'TRANSFERENCIA');