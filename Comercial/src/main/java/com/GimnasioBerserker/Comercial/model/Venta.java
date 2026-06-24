package com.GimnasioBerserker.Comercial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del socio es obligatorio")
    private Long socioId;

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precioUnitario;

    @NotNull(message = "El total es obligatorio")

    @Min(value = 1 , message = "El total debe ser mayor a 0 ")

    @Min(value = 1, message = "El total debe ser mayor a 0")

    private Integer total;

    private LocalDateTime fechaVenta;

    @PrePersist
    public void asignarFecha() {
        this.fechaVenta = LocalDateTime.now();
    }
}