package com.GimnasioBerserker.Comercial.controller;



import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos(){
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable Long id){
        return productoService.findById(id);
    }
    @PostMapping
    public Producto guardarProducto(@Valid @RequestBody Producto producto){
        return productoService.guardarproducto(producto);
    }

    @PutMapping("/{id}")
    public  Producto actualizarProducto(@Valid @RequestBody Producto producto, @PathVariable Long id){
        return productoService.actualizarproducto(id, producto);

    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
    }

    @GetMapping("/{id}/hateoas")
    public EntityModel<Producto> obtenerProductoHateoas(@PathVariable Long id) {
        Producto producto = productoService.findById(id);

        EntityModel<Producto> recurso = EntityModel.of(producto);

        recurso.add(linkTo(methodOn(ProductoController.class).obtenerProductoHateoas(id)).withSelfRel());
        recurso.add(linkTo(methodOn(ProductoController.class).listarProductos()).withRel("listar-productos"));
        recurso.add(linkTo(methodOn(ProductoController.class).buscarPorId(id)).withRel("buscar-producto"));
        recurso.add(linkTo(ProductoController.class).slash(id).withRel("eliminar-producto"));

        return recurso;
    }


}
