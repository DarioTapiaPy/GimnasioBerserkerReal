package com.GimnasioBerserker.Facturacion.dto;

import lombok.Data;

@Data
public class SocioDTO {

    private Long id;
    private String nombre;
    private String rut;
    private String email;
    private boolean estadoMembresia;
    private long planId;
}

