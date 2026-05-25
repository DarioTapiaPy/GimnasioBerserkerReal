package com.GimnasioBerserker.Inventario.Config;

import com.GimnasioBerserker.Inventario.Model.Inventario;
import com.GimnasioBerserker.Inventario.Repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (inventarioRepository.count() > 0) {
            log.info(">>> Inventario ya cargado en BD. Omitiendo inicialización.");
        } else {
            log.info(">>> Cargando máquinas y equipos iniciales en el puerto 8085...");



            Inventario maquina1 = new Inventario(
                    null,
                    "Cinta de Correr Pro 5000",
                    "Cardio",
                    "Operativa",
                    LocalDate.of(2024, 3, 15),
                    LocalDate.of(2024, 9, 15)
            );

            Inventario maquina2 = new Inventario(
                    null,
                    "Prensa de Piernas 45°",
                    "Musculación",
                    "Operativa",
                    LocalDate.now().minusMonths(1),
                    LocalDate.now().plusMonths(5)
            );

            Inventario maquina3 = new Inventario(
                    null,
                    "Bicicleta de Spinning",
                    "Cardio",
                    "En Reparación",
                    LocalDate.of(2024, 5, 20),
                    LocalDate.of(2024, 6, 20)
            );


            inventarioRepository.saveAll(List.of(maquina1, maquina2, maquina3));

            log.info(">>> Se han cargado 3 máquinas de prueba en el inventario correctamente.");
        }
    }
}