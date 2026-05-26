package com.GimnasioBerserker.Facturacion.controller;

import com.GimnasioBerserker.Facturacion.dto.PagoFacturaDTO;
import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturacion/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarPagos(){
        return pagoService.listarPagos();
    }
    @GetMapping("/{id}")
    public Pago BuscarPagoPorId(@PathVariable Long id){
        return pagoService.findById(id);
    }
    @PostMapping
    public Pago guardarPago(@Valid @RequestBody Pago pago){
        return pagoService.guardarPago(pago);
    }
    @PutMapping("/{id}")
    public Pago actualizarPago(@PathVariable Long id, @Valid @RequestBody Pago pago){
        return pagoService.actualizarPago(id, pago);
    }
    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id){
        pagoService.eliminarPago(id);
    }

    @GetMapping("/{id}/factura")
    public PagoFacturaDTO obtenerPagoConFactura(@PathVariable Long id){
        return pagoService.obtenerPagoConFactura(id);
    }

}