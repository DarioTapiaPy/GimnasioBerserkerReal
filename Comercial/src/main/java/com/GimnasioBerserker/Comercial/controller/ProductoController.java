package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/comercial/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Producto>> listarProductos() {

        List<EntityModel<Producto>> productos = productoService.listarProductos()
                .stream()
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(ProductoController.class).buscarPorId(producto.getId())).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
                ))
                .toList();

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Producto> buscarPorId(@PathVariable Long id) {

        Producto producto = productoService.findById(id);

        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos"),
                linkTo(methodOn(ProductoController.class).guardarProducto(producto)).withRel("crear-producto"),
                linkTo(methodOn(ProductoController.class).actualizarProducto(producto, id)).withRel("actualizar-producto")
        );
    }

    @PostMapping
    public EntityModel<Producto> guardarProducto(@Valid @RequestBody Producto producto) {

        Producto productoGuardado = productoService.guardarproducto(producto);

        return EntityModel.of(productoGuardado,
                linkTo(methodOn(ProductoController.class).buscarPorId(productoGuardado.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Producto> actualizarProducto(@Valid @RequestBody Producto producto, @PathVariable Long id) {

        Producto productoActualizado = productoService.actualizarproducto(id, producto);

        return EntityModel.of(productoActualizado,
                linkTo(methodOn(ProductoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
        );
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @GetMapping("/{id}/hateoas")
    public EntityModel<Producto> obtenerProductoHateoas(@PathVariable Long id) {
        return buscarPorId(id);
    }
}