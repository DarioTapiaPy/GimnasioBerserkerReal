package com.GimnasioBerserker.Facturacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "El ID de la factura es obligatorio")
    private Long facturaId;

    @NotNull(message = "La facha de pago es obligatoria ")
    private LocalDate fechaPago;

    @NotNull(message = "El monto de pago es obligatorio")
    @Min(value = 1 , message = "El monto de pago debe ser mayor de 0")
    private Double montoPago;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;
}
