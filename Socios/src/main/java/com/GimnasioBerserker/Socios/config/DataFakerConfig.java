package com.GimnasioBerserker.Socios.config;

import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.repository.MembresiaRepository;
import com.GimnasioBerserker.Socios.repository.SocioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataFakerConfig {

    /**
     * Carga datos de prueba al iniciar la aplicación usando DataFaker.
     * Solo inserta si hay menos de 5 socios en la base de datos,
     * para evitar duplicados en reinicios.
     */
    @Bean
    CommandLineRunner cargarSociosFalsos(SocioRepository socioRepository,
                                         MembresiaRepository membresiaRepository) {
        return args -> {
            // Utiliza Locale SPANISH para nombres en español
            Faker faker = new Faker(new Locale("es", "CL"));

            long totalMembresias = membresiaRepository.count();
            if (totalMembresias == 0) {
                System.out.println("[DataFaker] No hay membresías base; se omite la carga de socios falsos.");
                return;
            }

            if (socioRepository.count() < 5) {

                // Primeros dígitos de RUT chilenos de prueba
                String[] rutsFake = {
                        "11111111-1", "22222222-2", "33333333-3",
                        "44444444-4", "55555555-5"
                };

                for (int i = 0; i < 5; i++) {
                    String rut   = rutsFake[i];
                    String nombre = faker.name().fullName();
                    String email  = faker.internet().emailAddress();

                    // Evitar duplicados de RUT / email
                    if (socioRepository.buscarPorRut(rut).isEmpty()
                            && !emailExiste(socioRepository, email)) {

                        Socio socio = new Socio();
                        socio.setRut(rut);
                        socio.setNombre(nombre);
                        socio.setEmail(email);
                        socio.setEstadoMembresia(faker.bool().bool());
                        // planId: referencia a membresías ya cargadas (1–4)
                        long planId = faker.number().numberBetween(1L, totalMembresias + 1);
                        socio.setPlanId(planId);
                        // rutinaId: referencia a rutinas del microservicio Rutina (1–5)
                        socio.setRutinaId((long) faker.number().numberBetween(1, 6));

                        socioRepository.save(socio);
                    }
                }
                System.out.println("[DataFaker] Socios de prueba cargados correctamente.");
            }
        };
    }

    /** Utilidad para verificar si un email ya existe en BD. */
    private boolean emailExiste(SocioRepository repo, String email) {
        return repo.findAll().stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(email));
    }
}
