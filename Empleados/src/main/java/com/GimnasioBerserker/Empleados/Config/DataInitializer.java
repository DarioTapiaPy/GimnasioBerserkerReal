package com.GimnasioBerserker.Empleados.Config;

import com.GimnasioBerserker.Empleados.Model.Empleado;
import com.GimnasioBerserker.Empleados.Repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (empleadoRepository.count() > 0) {
            log.info(">>> Empleados ya cargados en BD. Omitiendo inicialización.");
        } else {
            log.info(">>> Cargando empleados iniciales en el puerto 8084...");


            empleadoRepository.save(new Empleado(
                    null,
                    "12345678-9",
                    "Jose Pérez",
                    "Entrenador",
                    "Musculación",
                    new BigDecimal("850000.00")
            ));

            empleadoRepository.save(new Empleado(
                    null,
                    "18765432-1",
                    "Laura Méndez",
                    "Nutricionista",
                    "Dietética Deportiva",
                    new BigDecimal("920000.50")
            ));

            log.info(">>> Se han cargado 2 empleados de prueba correctamente.");
        }
    }
}