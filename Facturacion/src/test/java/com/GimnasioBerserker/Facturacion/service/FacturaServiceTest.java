package com.GimnasioBerserker.Facturacion.service;

import com.GimnasioBerserker.Facturacion.client.SocioClient;
import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.repository.FacturaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private SocioClient socioClient;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    void listarFacturas_debeRetornarLista() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(1L);
        factura.setValor(50000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<Factura> resultado = facturaService.ListarFacturas();

        assertEquals(1, resultado.size());
        assertEquals(50000.0, resultado.get(0).getValor());
        verify(facturaRepository, times(1)).findAll();
    }

    @Test
    void buscarFacturaPorId_debeRetornarFactura() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(1L);
        factura.setValor(50000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));

        Factura resultado = facturaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50000.0, resultado.getValor());
        verify(facturaRepository, times(1)).findById(1L);
    }

    @Test
    void guardarFactura_debeGuardarFactura() {
        Factura factura = new Factura();
        factura.setIdSocio(1L);
        factura.setValor(60000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        Factura resultado = facturaService.guardarFactura(factura);

        assertNotNull(resultado);
        assertEquals(60000.0, resultado.getValor());
        verify(facturaRepository, times(1)).save(factura);
    }

    @Test
    void eliminarFactura_debeEliminarPorId() {
        Long id = 1L;

        facturaService.eliminarFactura(id);

        verify(facturaRepository, times(1)).deleteById(id);
    }
}