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

    @Bean
    CommandLineRunner cargarSociosFalsos(SocioRepository socioRepository,
                                         MembresiaRepository membresiaRepository) {
        return args -> {
            try {
                Faker faker = new Faker(new Locale("es", "CL"));

                // Intentamos contar, si la tabla no existe, lanzará una excepción que atraparemos abajo
                long totalMembresias = membresiaRepository.count();

                if (totalMembresias == 0) {
                    System.out.println("[DataFaker] No hay membresías base; se omite la carga.");
                    return;
                }

                if (socioRepository.count() < 5) {
                    String[] rutsFake = {"11111111-1", "22222222-2", "33333333-3", "44444444-4", "55555555-5"};

                    for (int i = 0; i < 5; i++) {
                        String rut = rutsFake[i];
                        String nombre = faker.name().fullName();
                        String email = faker.internet().emailAddress();

                        if (socioRepository.buscarPorRut(rut).isEmpty() && !emailExiste(socioRepository, email)) {
                            Socio socio = new Socio();
                            socio.setRut(rut);
                            socio.setNombre(nombre);
                            socio.setEmail(email);
                            socio.setEstadoMembresia(faker.bool().bool());
                            socio.setPlanId(faker.number().numberBetween(1L, totalMembresias + 1));
                            socio.setRutinaId((long) faker.number().numberBetween(1, 6));
                            socioRepository.save(socio);
                        }
                    }
                    System.out.println("[DataFaker] Socios de prueba cargados correctamente.");
                }
            } catch (Exception e) {
                // Si la BD no está lista, imprimimos el error sin detener el arranque del microservicio
                System.err.println("[DataFaker] No se pudieron cargar datos iniciales: " + e.getMessage());
            }
        };
    }

    private boolean emailExiste(SocioRepository repo, String email) {
        return repo.findAll().stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(email));
    }
}
