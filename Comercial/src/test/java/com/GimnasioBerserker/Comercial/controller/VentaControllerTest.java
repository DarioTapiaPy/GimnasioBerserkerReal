package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.dto.VentaRequestDTO;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.service.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
@AutoConfigureMockMvc(addFilters = false)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    @Test
    void listarVentas_debeRetornarOk() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setSocioId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(25000);
        venta.setTotal(50000);

        when(ventaService.listarVentas()).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/comercial/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].socioId").value(1))
                .andExpect(jsonPath("$[0].productoId").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precioUnitario").value(25000))
                .andExpect(jsonPath("$[0].total").value(50000));
    }

    @Test
    void buscarVentaPorId_debeRetornarOk() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setSocioId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(25000);
        venta.setTotal(50000);

        when(ventaService.findById(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/comercial/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.socioId").value(1))
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(25000))
                .andExpect(jsonPath("$.total").value(50000));
    }

    @Test
    void venderProducto_debeRetornarOk() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setSocioId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(25000);
        venta.setTotal(50000);

        when(ventaService.crearVentaProducto(any(VentaRequestDTO.class))).thenReturn(venta);

        String json = """
                {
                  "socioId": 1,
                  "productoId": 1,
                  "cantidad": 2
                }
                """;

        mockMvc.perform(post("/api/comercial/ventas/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.socioId").value(1))
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(25000))
                .andExpect(jsonPath("$.total").value(50000));
    }
}