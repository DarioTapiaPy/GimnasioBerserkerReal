package com.GimnasioBerserker.Empleados.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "empleados")
public class Empleado {

            //debo colocar @Valid en el controlador.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerar la pk
    private Long idEmp;

    @Column(name = "rut_emp", unique = true, nullable = false, length = 12) //esto me asegura de que no se repita
    @NotBlank(message = "El rut es obligatorio")
    private String runEmp;


    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100,message = "Maximo de 100 caracteres")//valida el objeto antes de ingresarlo a la bd
    @Column(name = "nombre_emp", length = 100, nullable = false)  //crea la columna con largo 100, columna NOT NULL
    private String nombreEmp;

    @NotBlank(message = "El cargo no puede estar vacio")
    @Column(name = "cargo", length = 50, nullable = false)   //columna NOT NULL
    private String cargoEmp;

    @NotBlank(message = "La especialidad no puede estar vacia")
    @Column(name = "especialidad",length = 100,nullable = false)   //columna NOT NULL
    private String especialidadEmp;


    @NotNull(message = "El sueldo no puede estar vacio")
    @Min(value = 0,message = "El sueldo no puede ser negativo")
    @Column(name = "sueldo", precision =10, scale = 2, nullable = false)
    private BigDecimal sueldoEmp;













}
