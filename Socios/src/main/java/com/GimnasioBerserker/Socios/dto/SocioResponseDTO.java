package com.GimnasioBerserker.Socios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocioResponseDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String estadoMembresia;
    private Long planId;
}