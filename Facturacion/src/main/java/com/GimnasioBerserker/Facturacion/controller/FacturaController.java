package com.GimnasioBerserker.Facturacion.controller;

import com.GimnasioBerserker.Facturacion.dto.SocioDTO;
import com.GimnasioBerserker.Facturacion.model.Factura;

import com.GimnasioBerserker.Facturacion.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> listarFacturas(){
        return facturaService.ListarFacturas();
    }

     @GetMapping("/{id}")
    public Factura buscarFacturaPorId(@PathVariable Long id){
        return facturaService.findById(id);
     }

     @PostMapping
     public Factura guardarFactura(@Valid @RequestBody Factura factura){
        return facturaService.guardarFactura(factura);
     }

    @PutMapping("/{id}")
    public Factura actualizarFactura(@PathVariable Long id,
                                     @Valid @RequestBody Factura factura) {
        return facturaService.actualizarFactura(id, factura);
    }
    @DeleteMapping("/{id}")
    public void eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
    }


    @GetMapping("/{id}/socios")
    public SocioDTO obtenerSocioDeFactura(@PathVariable Long id){
        return facturaService.obtenerSocioDeFactura(id);
    }



}


