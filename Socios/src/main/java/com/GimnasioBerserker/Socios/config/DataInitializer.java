package com.GimnasioBerserker.Socios.config;

import com.GimnasioBerserker.Socios.Model.Membresia;
import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.repository.MembresiaRepository;
import com.GimnasioBerserker.Socios.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {

    private final SocioRepository socioRepository;
    private final MembresiaRepository membresiaRepository;

    @Override
    public void run(String... args){
        if(socioRepository.count() > 0){
            log.info(">>> Socios ya cargados. Se omite inicialización.");
        }else{
        log.info(">>> Cargando socios iniciales...");
        socioRepository.save(new Socio(null, "22665456-9", "Roberto Medina", "rob.medina@gmail.com", true, 1L));
        socioRepository.save(new Socio(null, "21802504-8", "Julia Gonzalez", "jul.gonzalez@gmail.com", true, 3L));
        socioRepository.save(new Socio(null, "20503580-k", "David Ibañez ", "dav.ibanez@gmail.com", true, 2L));
        log.info(">>> 3 socios cargados OK.");}

        if(membresiaRepository.count() > 0){
            log.info(">>> Membresias ya cargadas. Se omite inicialización.");
        }else{
            log.info(">>> Cargando planes de membresías...");
            membresiaRepository.save(new Membresia(null, "Menusal", 28000, 1));
            membresiaRepository.save(new Membresia(null, "Trimestral", 25000, 3));
            membresiaRepository.save(new Membresia(null, "Semestral", 22500, 6));
            membresiaRepository.save(new Membresia(null, "Anual", 20000, 12));
        }
    }
}
