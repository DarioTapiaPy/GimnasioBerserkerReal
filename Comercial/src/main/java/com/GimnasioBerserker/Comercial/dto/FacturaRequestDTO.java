package com.GimnasioBerserker.Comercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequestDTO {

    private Long idSocio;
    private Double valor;
}