package com.GimnasioBerserker.Comercial.service;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos(){
        return productoRepository.findAll();

    }
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }


    public Producto guardarproducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarproducto(long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if (productoExistente != null) {
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setStock(producto.getStock());

            return productoRepository.save(productoExistente);
        }
        return null;


    }
    public void eliminarProducto(long id) {
        productoRepository.deleteById(id);
    }
}
