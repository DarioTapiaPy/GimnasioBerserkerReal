package com.GimnasioBerserker.Facturacion.config;

import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.repository.FacturaRepository;
import com.GimnasioBerserker.Facturacion.repository.PagoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataFakerConfig {

    @Bean
    CommandLineRunner cargarDatosFalsos(FacturaRepository facturaRepository, PagoRepository pagoRepository) {
        return args -> {
            Faker faker = new Faker();

            if (facturaRepository.count() < 10) {
                for (int i = 0; i < 10; i++) {
                    Factura factura = new Factura();

                    factura.setIdSocio((long) faker.number().numberBetween(1, 6));
                    factura.setValor((double) faker.number().numberBetween(20000, 100000));
                    factura.setFecha_facturacion(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));

                    facturaRepository.save(factura);
                }

                System.out.println("Facturas falsas creadas con DataFaker");
            }

            if (pagoRepository.count() < 10) {
                List<String> metodosPago = List.of("EFECTIVO", "DEBITO", "CREDITO", "TRANSFERENCIA");

                for (int i = 0; i < 10; i++) {
                    Pago pago = new Pago();

                    pago.setFacturaId((long) faker.number().numberBetween(1, 6));
                    pago.setFechaPago(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
                    pago.setMontoPago((double) faker.number().numberBetween(10000, 100000));
                    pago.setMetodoPago(metodosPago.get(faker.number().numberBetween(0, metodosPago.size())));

                    pagoRepository.save(pago);
                }

                System.out.println("Pagos falsos creados con DataFaker");
            }
        };
    }
}