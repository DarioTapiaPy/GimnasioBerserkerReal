package com.GimnasioBerserker.Socios.dto;

import com.GimnasioBerserker.Socios.dto.RutinaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocioConRutinaDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String estadoMembresia;
    private Long planId;
    private RutinaResponseDTO rutina;
}