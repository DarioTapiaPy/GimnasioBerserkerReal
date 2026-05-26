package com.GimnasioBerserker.Comercial.service;

import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta actualizarVenta(Long id, Venta venta) {
        Venta ventaExistente = ventaRepository.findById(id).orElse(null);

        if (ventaExistente != null) {
            ventaExistente.setSocioId(venta.getSocioId());
            ventaExistente.setTotal(venta.getTotal());

            return ventaRepository.save(ventaExistente);
        }

        return null;
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}