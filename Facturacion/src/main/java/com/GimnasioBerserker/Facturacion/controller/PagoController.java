package com.GimnasioBerserker.Facturacion.controller;


import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarPagos(){
        return pagoService.listarPagos();
    }
    @GetMapping("/{id}")
    public Pago actualizarPago(@PathVariable long id , @Valid @RequestBody Pago pago){
        return pagoService.actualizarPago(id,pago);

    }
    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable long id){
        pagoService.eliminarPago(id);

    }
}
