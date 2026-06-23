package com.GimnasioBerserker.Facturacion.service;

import com.GimnasioBerserker.Facturacion.dto.PagoFacturaDTO;
import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.repository.FacturaRepository;
import com.GimnasioBerserker.Facturacion.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void listarPagos_debeRetornarLista() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.now());
        pago.setMontoPago(28000.0);
        pago.setMetodoPago("DEBITO");

        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.listarPagos();

        assertEquals(1, resultado.size());
        assertEquals("DEBITO", resultado.get(0).getMetodoPago());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    void buscarPagoPorId_debeRetornarPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.now());
        pago.setMontoPago(28000.0);
        pago.setMetodoPago("DEBITO");

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Pago resultado = pagoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(28000.0, resultado.getMontoPago());
        verify(pagoRepository, times(1)).findById(1L);
    }

    @Test
    void guardarPago_debeGuardarPago() {
        Pago pago = new Pago();
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.now());
        pago.setMontoPago(30000.0);
        pago.setMetodoPago("EFECTIVO");

        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago resultado = pagoService.guardarPago(pago);

        assertNotNull(resultado);
        assertEquals(30000.0, resultado.getMontoPago());
        assertEquals("EFECTIVO", resultado.getMetodoPago());
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    void actualizarPago_debeActualizarPagoExistente() {
        Pago pagoExistente = new Pago();
        pagoExistente.setId(1L);
        pagoExistente.setFacturaId(1L);
        pagoExistente.setFechaPago(LocalDate.now());
        pagoExistente.setMontoPago(28000.0);
        pagoExistente.setMetodoPago("DEBITO");

        Pago pagoActualizado = new Pago();
        pagoActualizado.setFacturaId(2L);
        pagoActualizado.setFechaPago(LocalDate.now());
        pagoActualizado.setMontoPago(50000.0);
        pagoActualizado.setMetodoPago("CREDITO");

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoExistente));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoExistente);

        Pago resultado = pagoService.actualizarPago(1L, pagoActualizado);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getFacturaId());
        assertEquals(50000.0, resultado.getMontoPago());
        assertEquals("CREDITO", resultado.getMetodoPago());

        verify(pagoRepository, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(pagoExistente);
    }

    @Test
    void eliminarPago_debeEliminarPorId() {
        Long id = 1L;

        pagoService.eliminarPago(id);

        verify(pagoRepository, times(1)).deleteById(id);
    }

    @Test
    void obtenerPagoConFactura_debeRetornarDTO() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.now());
        pago.setMontoPago(28000.0);
        pago.setMetodoPago("DEBITO");

        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(5L);
        factura.setValor(50000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));

        PagoFacturaDTO resultado = pagoService.obtenerPagoConFactura(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getFacturaId());
        assertEquals(5L, resultado.getIdSocio());
        assertEquals(28000.0, resultado.getMontoPago());
        assertEquals("DEBITO", resultado.getMetodoPago());

        verify(pagoRepository, times(1)).findById(1L);
        verify(facturaRepository, times(1)).findById(1L);
    }
}