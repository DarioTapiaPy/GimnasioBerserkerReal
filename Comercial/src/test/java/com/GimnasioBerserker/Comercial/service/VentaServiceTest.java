package com.GimnasioBerserker.Comercial.service;

import com.GimnasioBerserker.Comercial.client.Facturacionclient;
import com.GimnasioBerserker.Comercial.dto.FacturaRequestDTO;
import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;
import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.repository.ProductoRepository;
import com.GimnasioBerserker.Comercial.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private Facturacionclient facturacionClient;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void listarVentas_debeRetornarLista() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setSocioId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(25000);
        venta.setTotal(50000);

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> resultado = ventaService.listarVentas();

        assertEquals(1, resultado.size());
        assertEquals(50000, resultado.get(0).getTotal());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void buscarVentaPorId_debeRetornarVenta() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setSocioId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(25000);
        venta.setTotal(50000);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Venta resultado = ventaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50000, resultado.getTotal());
        verify(ventaRepository, times(1)).findById(1L);
    }

    @Test
    void crearVentaProducto_debeCrearVentaDescontarStockYCrearFactura() {
        VentaRequestDTO request = new VentaRequestDTO();
        request.setSocioId(1L);
        request.setProductoId(1L);
        request.setCantidad(2);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Proteina Whey");
        producto.setCategoria("Suplemento");
        producto.setPrecio(25000);
        producto.setStock(10);

        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(1L);
        ventaGuardada.setSocioId(1L);
        ventaGuardada.setProductoId(1L);
        ventaGuardada.setCantidad(2);
        ventaGuardada.setPrecioUnitario(25000);
        ventaGuardada.setTotal(50000);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaGuardada);

        Venta resultado = ventaService.crearVentaProducto(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getSocioId());
        assertEquals(1L, resultado.getProductoId());
        assertEquals(2, resultado.getCantidad());
        assertEquals(25000, resultado.getPrecioUnitario());
        assertEquals(50000, resultado.getTotal());

        assertEquals(8, producto.getStock());

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(producto);
        verify(ventaRepository, times(1)).save(any(Venta.class));
        verify(facturacionClient, times(1)).crearFactura(any(FacturaRequestDTO.class));
    }

    @Test
    void crearVentaProducto_debeLanzarErrorSiNoHayStock() {
        VentaRequestDTO request = new VentaRequestDTO();
        request.setSocioId(1L);
        request.setProductoId(1L);
        request.setCantidad(20);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Proteina Whey");
        producto.setCategoria("Suplemento");
        producto.setPrecio(25000);
        producto.setStock(5);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            ventaService.crearVentaProducto(request);
        });

        assertEquals("No hay stock suficiente", error.getMessage());

        verify(productoRepository, times(1)).findById(1L);
        verify(ventaRepository, never()).save(any(Venta.class));
        verify(facturacionClient, never()).crearFactura(any(FacturaRequestDTO.class));
    }

    @Test
    void eliminarVenta_debeEliminarPorId() {
        Long id = 1L;

        ventaService.eliminarVenta(id);

        verify(ventaRepository, times(1)).deleteById(id);
    }
}