package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/comercial/productos")
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


}
