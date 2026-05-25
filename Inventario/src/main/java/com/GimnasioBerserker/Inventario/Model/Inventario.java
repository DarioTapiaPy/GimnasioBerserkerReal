package com.GimnasioBerserker.Inventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventario")



public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la máquina no puede estar vacío")
    @Size(max = 100, message = "Máximo de 100 caracteres")
    @Column(name = "nombre_maquina", length = 100, nullable = false)
    private String nombreMaquina;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Size(max = 50, message = "Máximo de 50 caracteres")
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "Máximo de 20 caracteres")
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

    // Usamos LocalDate porque tu diagrama pide DATE (solo fecha, sin horas)
    @NotNull(message = "La fecha de última mantención es obligatoria")
    @Column(name = "ultima_mantencion", nullable = false)
    private LocalDate ultimaMantencion;

    @NotNull(message = "La fecha de próxima mantención es obligatoria")
    @Column(name = "proxima_mantencion", nullable = false)
    private LocalDate proximaMantencion;
}
