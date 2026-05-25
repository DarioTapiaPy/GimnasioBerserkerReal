package com.GimnasioBerserker.Facturacion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacturaDto {
    private long socioid;
    private LocalDateTime fecha_facturacion;
    private double valor ;
}
