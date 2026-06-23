package com.GimnasioBerserker.Comercial.controller;


import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;

import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @GetMapping("/{id}/hateoas")
    public EntityModel<Venta> obtenerVentaHateoas(@PathVariable Long id) {
        Venta venta = ventaService.findById(id);

        EntityModel<Venta> recurso = EntityModel.of(venta);

        recurso.add(linkTo(methodOn(VentaController.class).obtenerVentaHateoas(id)).withSelfRel());
        recurso.add(linkTo(methodOn(VentaController.class).listarVentas()).withRel("listar-ventas"));
        recurso.add(linkTo(methodOn(VentaController.class).buscarVentaPorId(id)).withRel("buscar-venta"));
        recurso.add(linkTo(VentaController.class).slash(id).withRel("eliminar-venta"));

        return recurso;
    }
}