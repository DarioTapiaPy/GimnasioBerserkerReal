package com.GimnasioBerserker.Comercial.controller;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void listarProductos_debeRetornarOk() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Proteina Whey");
        producto.setCategoria("Suplemento");
        producto.setPrecio(25000);
        producto.setStock(10);
        producto.verificarStock();

        when(productoService.listarProductos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/comercial/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(content().string(containsString("Proteina Whey")))
                .andExpect(content().string(containsString("Suplemento")))
                .andExpect(content().string(containsString("Disponible")));
    }

    @Test
    void buscarProductoPorId_debeRetornarOk() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Guantes Gym");
        producto.setCategoria("Accesorio");
        producto.setPrecio(12000);
        producto.setStock(0);
        producto.verificarStock();

        when(productoService.findById(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/comercial/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Guantes Gym"))
                .andExpect(jsonPath("$.categoria").value("Accesorio"))
                .andExpect(jsonPath("$.precio").value(12000))
                .andExpect(jsonPath("$.stock").value(0))
                .andExpect(jsonPath("$.estadoStock").value("Stock no disponible"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.todos-los-productos.href").exists());
    }

    @Test
    void guardarProducto_debeRetornarOk() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Polera Deportiva");
        producto.setCategoria("Ropa deportiva");
        producto.setPrecio(18000);
        producto.setStock(20);
        producto.verificarStock();

        when(productoService.guardarproducto(any(Producto.class))).thenReturn(producto);

        String json = """
                {
                  "nombre": "Polera Deportiva",
                  "categoria": "Ropa deportiva",
                  "precio": 18000,
                  "stock": 20
                }
                """;

        mockMvc.perform(post("/api/comercial/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Polera Deportiva"))
                .andExpect(jsonPath("$.categoria").value("Ropa deportiva"))
                .andExpect(jsonPath("$.precio").value(18000))
                .andExpect(jsonPath("$.stock").value(20))
                .andExpect(jsonPath("$.estadoStock").value("Disponible"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.todos-los-productos.href").exists());
    }
}