package com.GimnasioBerserker.Socios.config;

import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {

    private final SocioRepository repository;

    @Override
    public void run(String... args){
        if(repository.count() > 0){
            log.info(">>> Socios ya cargados. Se omite inicialización.");
            return;
        }
        log.info(">>> Cargando socios iniciales...");
        repository.save(new Socio(null, "22665456-9", "Roberto Medina", "rob.medina@gmail.com", false, 1L));
        repository.save(new Socio(null, "21802504-8", "Julia Gonzalez", "jul.gonzalez@gmail.com", true, 3L));
        repository.save(new Socio(null, "20503580-k", "David Ibañez ", "dav.ibanez@gmail.com", true, 2L));
        log.info(">>> 3 socios cargados OK.");
    }
}
