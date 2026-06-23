package com.GimnasioBerserker.Rutina.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data // Si no usas Lombok, genera los Getters y Setters a mano
@Entity
@Table(name = "rutinas")
@Schema(description = "Entidad que representa una rutina de entrenamiento asignada a un socio")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la rutina", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre descriptivo de la rutina", example = "Torso/Pierna")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Nivel de dificultad", example = "Intermedio")
    private String nivel;

    @Column(nullable = false)
    @Schema(description = "Frecuencia semanal en días", example = "4")
    private Integer diasPorSemana;

    @Schema(description = "ID del socio al que pertenece la rutina", example = "105")
    private Long socioId;
}