package com.GimnasioBerserker.Comercial.config;

import com.GimnasioBerserker.Comercial.model.Producto;
import com.GimnasioBerserker.Comercial.model.Venta;
import com.GimnasioBerserker.Comercial.repository.ProductoRepository;
import com.GimnasioBerserker.Comercial.repository.VentaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataFakerConfig {

    @Bean
    CommandLineRunner cargarDatosFalsos(ProductoRepository productoRepository, VentaRepository ventaRepository) {
        return args -> {
            Faker faker = new Faker();

            if (productoRepository.count() < 10) {
                List<String> categorias = List.of(
                        "Suplemento",
                        "Ropa deportiva",
                        "Accesorio",
                        "Bebida",
                        "Equipamiento"
                );

                for (int i = 0; i < 10; i++) {
                    Producto producto = new Producto();

                    producto.setNombre(faker.commerce().productName());
                    producto.setCategoria(categorias.get(faker.number().numberBetween(0, categorias.size())));
                    producto.setPrecio(faker.number().numberBetween(10000, 60000));
                    producto.setStock(faker.number().numberBetween(0, 30));

                    productoRepository.save(producto);
                }

                System.out.println("Productos falsos creados con DataFaker");
            }

            if (ventaRepository.count() < 10) {
                List<Producto> productos = productoRepository.findAll();

                if (!productos.isEmpty()) {
                    for (int i = 0; i < 10; i++) {
                        Producto producto = productos.get(faker.number().numberBetween(0, productos.size()));

                        int cantidad = faker.number().numberBetween(1, 5);
                        int precioUnitario = producto.getPrecio();
                        int total = cantidad * precioUnitario;

                        Venta venta = new Venta();

                        venta.setSocioId((long) faker.number().numberBetween(1, 6));
                        venta.setProductoId(producto.getId());
                        venta.setCantidad(cantidad);
                        venta.setPrecioUnitario(precioUnitario);
                        venta.setTotal(total);

                        ventaRepository.save(venta);
                    }

                    System.out.println("Ventas falsas creadas con DataFaker");
                }
            }
        };
    }
}