package com.GimnasioBerserker.Socios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SocioRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 11)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "El estado de la membresía es obligatorio (true para activo, false para inactivo)")
    private Boolean estadoMembresia;

    @NotNull(message = "El planId es obligatorio")
    private Long planId;
}