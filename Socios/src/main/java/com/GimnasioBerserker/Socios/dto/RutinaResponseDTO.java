package com.GimnasioBerserker.Socios.dto;

import lombok.Data;
import java.util.List;

@Data
public class RutinaResponseDTO {
    private Long id;
    private String nombre;
    private String objetivo;
    private int duracionSemanas;
}