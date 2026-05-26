package com.GimnasioBerserker.Comercial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio ")
    private String nombre;

    @NotBlank(message = "La categoria es obligatoria ")
    private String categoria;

    @NotNull(message = "El precio es obligatorio ")
    @Min(value = 1, message = "el precio debe ser mayor a 0 ")
    private Integer precio;


    @NotNull(message = "El stock es obligatorio ")
    @Min(value = 0 , message = "El stock no puede ser negativo ")
    private Integer stock;

    private String estadoStock ;
     @PrePersist
     @PreUpdate
    public void verificarStock(){
         if(stock == 0){
             estadoStock = "Stock no disponible";
         }else{
             estadoStock = "Disponible";
         }
     }
}
