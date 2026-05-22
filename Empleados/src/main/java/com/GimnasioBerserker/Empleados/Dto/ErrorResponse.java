package com.GimnasioBerserker.Empleados.Dto;

import java.time.LocalDateTime;

public record ErrorResponse(String mensaje, int codigo, LocalDateTime timestamp) {

    public ErrorResponse(String mensaje, int codigo){
        this(mensaje, codigo, LocalDateTime.now());
    }


}
