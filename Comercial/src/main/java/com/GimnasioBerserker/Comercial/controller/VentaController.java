package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/comercial/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Venta>> listarVentas() {

        List<EntityModel<Venta>> ventas = ventaService.listarVentas()
                .stream()
                .map(venta -> EntityModel.of(venta,
                        linkTo(methodOn(VentaController.class).buscarVentaPorId(venta.getId())).withSelfRel(),
                        linkTo(methodOn(VentaController.class).listarVentas()).withRel("todas-las-ventas")
                ))
                .toList();

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).listarVentas()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Venta> buscarVentaPorId(@PathVariable Long id) {

        Venta venta = ventaService.findById(id);

        return EntityModel.of(venta,
                linkTo(methodOn(VentaController.class).buscarVentaPorId(id)).withSelfRel(),
                linkTo(methodOn(VentaController.class).listarVentas()).withRel("todas-las-ventas"),
                linkTo(methodOn(VentaController.class).obtenerVentaHateoas(id)).withRel("venta-hateoas")
        );
    }

    @PostMapping("/producto")
    public EntityModel<Venta> venderProducto(@Valid @RequestBody VentaRequestDTO request) {

        Venta ventaCreada = ventaService.crearVentaProducto(request);

        return EntityModel.of(ventaCreada,
                linkTo(methodOn(VentaController.class).buscarVentaPorId(ventaCreada.getId())).withSelfRel(),
                linkTo(methodOn(VentaController.class).listarVentas()).withRel("todas-las-ventas")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Venta> actualizarVenta(@PathVariable Long id, @Valid @RequestBody Venta venta) {

        Venta ventaActualizada = ventaService.actualizarVenta(id, venta);

        return EntityModel.of(ventaActualizada,
                linkTo(methodOn(VentaController.class).buscarVentaPorId(id)).withSelfRel(),
                linkTo(methodOn(VentaController.class).listarVentas()).withRel("todas-las-ventas")
        );
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }

    @GetMapping("/{id}/hateoas")
    public EntityModel<Venta> obtenerVentaHateoas(@PathVariable Long id) {
        return buscarVentaPorId(id);
    }
}