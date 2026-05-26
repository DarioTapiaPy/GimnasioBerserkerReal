package com.GimnasioBerserker.Comercial.service;

import com.GimnasioBerserker.Comercial.dto.FacturaRequestDTO;
import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;
import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.repository.ProductoRepository;
import com.GimnasioBerserker.Comercial.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private com.GimnasioBerserker.Comercial.client.Facturacionclient facturacionClient;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta crearVentaProducto(VentaRequestDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("No hay stock suficiente");
        }

        Integer total = producto.getPrecio() * request.getCantidad();

        producto.setStock(producto.getStock() - request.getCantidad());
        productoRepository.save(producto);

        Venta venta = new Venta();
        venta.setSocioId(request.getSocioId());
        venta.setProductoId(producto.getId());
        venta.setCantidad(request.getCantidad());
        venta.setPrecioUnitario(producto.getPrecio());
        venta.setTotal(total);

        Venta ventaGuardada = ventaRepository.save(venta);

        FacturaRequestDTO facturaRequest = new FacturaRequestDTO(
                ventaGuardada.getSocioId(),
                ventaGuardada.getTotal().doubleValue()
        );
        facturacionClient.crearFactura(facturaRequest);

        return ventaGuardada;
    }

    public Venta actualizarVenta(Long id, Venta venta) {
        Venta ventaExistente = ventaRepository.findById(id).orElse(null);

        if (ventaExistente != null) {
            ventaExistente.setSocioId(venta.getSocioId());
            ventaExistente.setProductoId(venta.getProductoId());
            ventaExistente.setCantidad(venta.getCantidad());
            ventaExistente.setPrecioUnitario(venta.getPrecioUnitario());
            ventaExistente.setTotal(venta.getTotal());

            return ventaRepository.save(ventaExistente);
        }

        return null;
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}