package com.GimnasioBerserker.Facturacion.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoDto {

    private Long facturaId;
    private LocalDate fechaPago;
    private Double montoPago;
    private String metodoPago;
}