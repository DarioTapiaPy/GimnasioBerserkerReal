package com.GimnasioBerserker.Facturacion.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PagoFacturaDTO {
    private long id;
    private long facturaId;
    private LocalDate fechaPago;
    private double montoPago;
    private String metodoPago;

    private long idSocio;
    private double valor;
    private LocalDateTime fecha_facturacion;

}
