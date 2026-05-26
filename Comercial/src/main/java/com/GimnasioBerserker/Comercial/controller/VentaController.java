package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercial/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}")
    public Venta buscarVentaPorId(@PathVariable Long id) {
        return ventaService.findById(id);
    }

    @PostMapping("/producto")
    public Venta venderProducto(@Valid @RequestBody VentaRequestDTO request) {
        return ventaService.crearVentaProducto(request);
    }

    @PutMapping("/{id}")
    public Venta actualizarVenta(@PathVariable Long id, @Valid @RequestBody Venta venta) {
        return ventaService.actualizarVenta(id, venta);
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }
}