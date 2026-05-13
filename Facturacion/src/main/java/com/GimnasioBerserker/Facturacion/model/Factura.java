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
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "Facturas")

public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio ")
    private String cliente;

    @NotBlank(message = "El RUT es obligatorio ")
    @Pattern(
            regexp = "^[0-9]{7,8}-[0-9kK]{1}$",
            message = "Formato de Rut invalido"
    )
    private String rut;

    @NotNull(message = "El total es obligatorio ")
    @Min(value = 1 , message = "El total debe ser mayor a 0")
    private Double valor;

    @NotNull(message = "La fecha es obligatoria ")
    private LocalDate fecha_facturacion;

    @Enumerated(EnumType.STRING)
    private TipoMembresia tipoMembresia;

}
