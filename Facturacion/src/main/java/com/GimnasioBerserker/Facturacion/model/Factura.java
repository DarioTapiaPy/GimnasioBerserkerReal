package com.GimnasioBerserker.Facturacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "Facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del socio es obligatorio")
    private Long idSocio;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "20000.0", message = "El total debe ser mayor o igual a 20000")
    private Double valor;

    private LocalDateTime fecha_facturacion;

    @PrePersist
    public void asignarFecha() {
        this.fecha_facturacion = LocalDateTime.now();
    }
}
