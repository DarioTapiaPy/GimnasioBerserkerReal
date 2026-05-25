package com.GimnasioBerserker.Facturacion.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FacturaSocioDTO {

   private long id;
   private long idSocio;
   private double valor ;
   private LocalDateTime fecha_facturacion;
   private String rut;
   private String nombre;
   private String email;
   private boolean estadoMembresia ;
   private long planId;
}