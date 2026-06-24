package com.GimnasioBerserker.Facturacion.controller;

import com.GimnasioBerserker.Facturacion.dto.FacturaSocioDTO;
import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/facturacion/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public CollectionModel<EntityModel<Factura>> listarFacturas() {

        List<EntityModel<Factura>> facturas = facturaService.ListarFacturas()
                .stream()
                .map(factura -> EntityModel.of(factura,
                        linkTo(methodOn(FacturaController.class).buscarFacturaPorId(factura.getId())).withSelfRel(),
                        linkTo(methodOn(FacturaController.class).obtenerFacturaConSocio(factura.getId())).withRel("factura-con-socio")
                ))
                .toList();

        return CollectionModel.of(facturas,
                linkTo(methodOn(FacturaController.class).listarFacturas()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Factura> buscarFacturaPorId(@PathVariable Long id) {

        Factura factura = facturaService.findById(id);

        return EntityModel.of(factura,
                linkTo(methodOn(FacturaController.class).buscarFacturaPorId(id)).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todas-las-facturas"),
                linkTo(methodOn(FacturaController.class).obtenerFacturaConSocio(id)).withRel("factura-con-socio")
        );
    }

    @PostMapping
    public EntityModel<Factura> guardarFactura(@Valid @RequestBody Factura factura) {

        Factura facturaGuardada = facturaService.guardarFactura(factura);

        return EntityModel.of(facturaGuardada,
                linkTo(methodOn(FacturaController.class).buscarFacturaPorId(facturaGuardada.getId())).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todas-las-facturas")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Factura> actualizarFactura(@PathVariable Long id, @Valid @RequestBody Factura factura) {

        Factura facturaActualizada = facturaService.actualizarFactura(id, factura);

        return EntityModel.of(facturaActualizada,
                linkTo(methodOn(FacturaController.class).buscarFacturaPorId(id)).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todas-las-facturas"),
                linkTo(methodOn(FacturaController.class).obtenerFacturaConSocio(id)).withRel("factura-con-socio")
        );
    }

    @DeleteMapping("/{id}")
    public void eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
    }

    // Endpoint especial que usa Feign o comunicación interna
    @GetMapping("/{id}/socios")
    public EntityModel<FacturaSocioDTO> obtenerFacturaConSocio(@PathVariable Long id) {

        FacturaSocioDTO facturaSocioDTO = facturaService.obtenerFacturaConSocio(id);

        return EntityModel.of(facturaSocioDTO,
                linkTo(methodOn(FacturaController.class).obtenerFacturaConSocio(id)).withSelfRel(),
                linkTo(methodOn(FacturaController.class).buscarFacturaPorId(id)).withRel("factura"),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todas-las-facturas")
        );
    }
}
