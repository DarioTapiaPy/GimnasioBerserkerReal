package com.GimnasioBerserker.Facturacion.controller;

import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.service.FacturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
@AutoConfigureMockMvc(addFilters = false)
class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacturaService facturaService;

    @Test
    void listarFacturas_debeRetornarOk() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(1L);
        factura.setValor(50000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(facturaService.ListarFacturas()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturacion/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].idSocio").value(1))
                .andExpect(jsonPath("$[0].valor").value(50000.0));
    }

    @Test
    void buscarFacturaPorId_debeRetornarOk() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(1L);
        factura.setValor(50000.0);
        factura.setFecha_facturacion(LocalDateTime.now());

        when(facturaService.findById(1L)).thenReturn(factura);

        mockMvc.perform(get("/api/facturacion/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idSocio").value(1))
                .andExpect(jsonPath("$.valor").value(50000.0));
    }

    @Test
    void guardarFactura_debeRetornarOk() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setIdSocio(1L);
        factura.setValor(60000.0);
        factura.setFecha_facturacion(LocalDateTime.of(2026, 6, 22, 22, 30));

        when(facturaService.guardarFactura(any(Factura.class))).thenReturn(factura);

        String json = """
                {
                  "idSocio": 1,
                  "valor": 60000,
                  "fecha_facturacion": "2026-06-22T22:30:00"
                }
                """;

        mockMvc.perform(post("/api/facturacion/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idSocio").value(1))
                .andExpect(jsonPath("$.valor").value(60000.0));
    }
}
