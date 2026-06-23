package com.GimnasioBerserker.Facturacion.controller;

import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.service.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    @Test
    void listarPagos_debeRetornarOk() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.of(2026, 6, 22));
        pago.setMontoPago(28000.0);
        pago.setMetodoPago("DEBITO");

        when(pagoService.listarPagos()).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/facturacion/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].facturaId").value(1))
                .andExpect(jsonPath("$[0].montoPago").value(28000.0))
                .andExpect(jsonPath("$[0].metodoPago").value("DEBITO"));
    }

    @Test
    void buscarPagoPorId_debeRetornarOk() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.of(2026, 6, 22));
        pago.setMontoPago(28000.0);
        pago.setMetodoPago("DEBITO");

        when(pagoService.findById(1L)).thenReturn(pago);

        mockMvc.perform(get("/api/facturacion/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.facturaId").value(1))
                .andExpect(jsonPath("$.montoPago").value(28000.0))
                .andExpect(jsonPath("$.metodoPago").value("DEBITO"));
    }

    @Test
    void guardarPago_debeRetornarOk() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setFacturaId(1L);
        pago.setFechaPago(LocalDate.of(2026, 6, 22));
        pago.setMontoPago(30000.0);
        pago.setMetodoPago("EFECTIVO");

        when(pagoService.guardarPago(any(Pago.class))).thenReturn(pago);

        String json = """
                {
                  "facturaId": 1,
                  "fechaPago": "2026-06-22",
                  "montoPago": 30000,
                  "metodoPago": "EFECTIVO"
                }
                """;

        mockMvc.perform(post("/api/facturacion/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.facturaId").value(1))
                .andExpect(jsonPath("$.montoPago").value(30000.0))
                .andExpect(jsonPath("$.metodoPago").value("EFECTIVO"));
    }
}