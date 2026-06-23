package com.GimnasioBerserker.Rutina.config;

import com.GimnasioBerserker.Rutina.model.Ejercicio;
import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.repository.RutinaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class DataFakerConfig {

    /** Grupos musculares típicos de un gimnasio. */
    private static final List<String> GRUPOS_MUSCULARES = List.of(
            "Pecho", "Espalda", "Piernas", "Hombros",
            "Bíceps", "Tríceps", "Abdomen", "Glúteos"
    );

    /** Objetivos de entrenamiento para rutinas. */
    private static final List<String> OBJETIVOS = List.of(
            "Fuerza", "Hipertrofia", "Resistencia",
            "Pérdida de grasa", "Tonificación", "Rehabilitación"
    );

    /**
     * Genera rutinas de entrenamiento de prueba usando DataFaker.
     * Solo inserta si hay menos de 5 rutinas para evitar duplicados.
     */
    @Bean
    CommandLineRunner cargarRutinasFalsas(RutinaRepository rutinaRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("es", "CL"));

            if (rutinaRepository.count() < 5) {

                for (int i = 0; i < 5; i++) {
                    Rutina rutina = new Rutina();
                    rutina.setNombre("Rutina " + faker.sports().sport() + " " + (i + 1));
                    rutina.setObjetivo(OBJETIVOS.get(faker.number().numberBetween(0, OBJETIVOS.size())));
                    rutina.setDuracionSemanas(faker.number().numberBetween(4, 16));

                    // Cada rutina tiene entre 3 y 6 ejercicios
                    int cantEjercicios = faker.number().numberBetween(3, 7);
                    List<Ejercicio> ejercicios = new ArrayList<>();

                    for (int j = 0; j < cantEjercicios; j++) {
                        Ejercicio ejercicio = new Ejercicio();
                        ejercicio.setNombre(faker.educator().course() + " Press");
                        ejercicio.setSeries(faker.number().numberBetween(3, 6));
                        ejercicio.setRepeticiones(faker.number().numberBetween(8, 15));
                        ejercicio.setGrupoMuscular(
                                GRUPOS_MUSCULARES.get(
                                        faker.number().numberBetween(0, GRUPOS_MUSCULARES.size())
                                )
                        );
                        ejercicios.add(ejercicio);
                    }

                    rutina.setEjercicios(ejercicios);
                    rutinaRepository.save(rutina);
                }

                System.out.println("[DataFaker] Rutinas de prueba cargadas correctamente.");
            }
        };
    }
}
