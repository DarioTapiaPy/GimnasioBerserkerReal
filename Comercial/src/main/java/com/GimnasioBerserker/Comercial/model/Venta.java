package com.GimnasioBerserker.Comercial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.XMLEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @NotNull (message = "El id del socio es obligatorio")
    private Long socioId;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 1 , message = "el total debe ser mayor a 0 ")
    private Integer total;

    private LocalDateTime fechaVenta;

    @PrePersist
    public void asignarFecha() {
        this.fechaVenta = LocalDateTime.now();
    }

}
