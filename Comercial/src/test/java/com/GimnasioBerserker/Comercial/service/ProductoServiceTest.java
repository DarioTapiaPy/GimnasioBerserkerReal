package com.GimnasioBerserker.Comercial.service;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void listarProductos_debeRetornarLista() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Proteina Whey");
        producto.setCategoria("Suplemento");
        producto.setPrecio(25000);
        producto.setStock(10);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.listarProductos();

        assertEquals(1, resultado.size());
        assertEquals("Proteina Whey", resultado.get(0).getNombre());
        assertEquals(25000, resultado.get(0).getPrecio());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void buscarProductoPorId_debeRetornarProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Guantes Gym");
        producto.setCategoria("Accesorio");
        producto.setPrecio(12000);
        producto.setStock(5);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Guantes Gym", resultado.getNombre());

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void guardarProducto_debeGuardarProducto() {
        Producto producto = new Producto();
        producto.setNombre("Polera Deportiva");
        producto.setCategoria("Ropa deportiva");
        producto.setPrecio(18000);
        producto.setStock(20);

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardarproducto(producto);

        assertNotNull(resultado);
        assertEquals("Polera Deportiva", resultado.getNombre());
        assertEquals(18000, resultado.getPrecio());

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void eliminarProducto_debeEliminarPorId() {
        Long id = 1L;

        productoService.eliminarProducto(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
}