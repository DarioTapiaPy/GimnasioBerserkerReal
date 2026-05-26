package com.GimnasioBerserker.Facturacion.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
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

    @NotNull(message = "El id del socio es obligatorio ")
   private Long idSocio;

    @NotNull(message = "El total es obligatorio ")
    @Min(value = 20000 , message = "El total debe ser mayor a 0")
    private Double valor;

    @NotNull(message = "La fecha es obligatoria ")
    private LocalDateTime fecha_facturacion;



}
