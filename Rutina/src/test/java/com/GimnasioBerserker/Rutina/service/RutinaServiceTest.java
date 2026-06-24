package com.GimnasioBerserker.Rutina.service;

import com.GimnasioBerserker.Rutina.model.Ejercicio;
import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.repository.RutinaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para RutinaService.
 *
 * CP-002 – Obtener todas las rutinas    (Unitario)
 * CP-004 – Crear nueva rutina           (Unitario)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RutinaService – Pruebas unitarias")
class RutinaServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RutinaService rutinaService;

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Ejercicio crearEjercicio(Long id, String nombre, int series,
                                     int reps, String grupo) {
        Ejercicio e = new Ejercicio();
        e.setId(id);
        e.setNombre(nombre);
        e.setSeries(series);
        e.setRepeticiones(reps);
        e.setGrupoMuscular(grupo);
        return e;
    }

    private Rutina crearRutina(Long id, String nombre, String objetivo,
                               int semanas, List<Ejercicio> ejercicios) {
        Rutina r = new Rutina();
        r.setId(id);
        r.setNombre(nombre);
        r.setObjetivo(objetivo);
        r.setDuracionSemanas(semanas);
        r.setEjercicios(ejercicios);
        return r;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // CP-002 · Obtener todas las rutinas · Unitario
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("CP-002 – Obtener todas las rutinas: debe retornar la lista completa del repositorio")
    void cp002_obtenerTodasLasRutinas_debeRetornarListaCompleta() {

        // ── Given ─────────────────────────────────────────────────────────────
        Rutina rutina1 = crearRutina(1L, "Full Body", "Tonificación general", 8,
                List.of(
                        crearEjercicio(1L, "Press de banca", 4, 10, "Pecho"),
                        crearEjercicio(2L, "Sentadilla",     4, 12, "Piernas")
                ));

        Rutina rutina2 = crearRutina(2L, "Piernas y Glúteos", "Hipertrofia", 12,
                List.of(
                        crearEjercicio(3L, "Peso muerto",     3, 8,  "Piernas"),
                        crearEjercicio(4L, "Hip Thrust",      4, 15, "Glúteos")
                ));

        Rutina rutina3 = crearRutina(3L, "Torso Completo", "Fuerza", 6,
                List.of(
                        crearEjercicio(5L, "Dominadas",       3, 8, "Espalda"),
                        crearEjercicio(6L, "Remo con barra",  3, 10,"Espalda")
                ));

        when(rutinaRepository.findAll()).thenReturn(List.of(rutina1, rutina2, rutina3));

        // ── When ──────────────────────────────────────────────────────────────
        List<Rutina> resultado = rutinaService.obtenerTodas();

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado,                 "La lista de rutinas no debe ser nula");
        assertEquals(3, resultado.size(),        "Deben retornarse exactamente 3 rutinas");

        // Verificar primera rutina
        assertEquals(1L,               resultado.get(0).getId(),              "ID de la primera rutina debe ser 1");
        assertEquals("Full Body",      resultado.get(0).getNombre(),          "Nombre de la primera rutina debe coincidir");
        assertEquals("Tonificación general", resultado.get(0).getObjetivo(),  "Objetivo debe coincidir");
        assertEquals(8,                resultado.get(0).getDuracionSemanas(), "Duración en semanas debe ser 8");
        assertEquals(2,                resultado.get(0).getEjercicios().size(),"Debe tener 2 ejercicios");

        // Verificar segunda rutina
        assertEquals("Piernas y Glúteos", resultado.get(1).getNombre());
        assertEquals("Hipertrofia",        resultado.get(1).getObjetivo());

        // Verificar tercera rutina
        assertEquals("Fuerza",            resultado.get(2).getObjetivo());

        verify(rutinaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("CP-002 – Obtener todas las rutinas: repositorio vacío debe retornar lista vacía")
    void cp002_repositorioVacio_debeRetornarListaVacia() {

        // ── Given ─────────────────────────────────────────────────────────────
        when(rutinaRepository.findAll()).thenReturn(List.of());

        // ── When ──────────────────────────────────────────────────────────────
        List<Rutina> resultado = rutinaService.obtenerTodas();

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado,          "El resultado no debe ser nulo");
        assertTrue(resultado.isEmpty(),   "La lista debe estar vacía cuando no hay rutinas");

        verify(rutinaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("CP-002 – Obtener todas las rutinas: debe preservar todos los datos de cada rutina")
    void cp002_obtenerTodas_debePreservarDatosCompletos() {

        // ── Given ─────────────────────────────────────────────────────────────
        Ejercicio ejercicio = crearEjercicio(10L, "Curl de bíceps", 3, 12, "Bíceps");
        Rutina rutina = crearRutina(5L, "Brazo Completo", "Hipertrofia", 4, List.of(ejercicio));

        when(rutinaRepository.findAll()).thenReturn(List.of(rutina));

        // ── When ──────────────────────────────────────────────────────────────
        List<Rutina> resultado = rutinaService.obtenerTodas();

        // ── Then ──────────────────────────────────────────────────────────────
        assertEquals(1, resultado.size());
        Rutina r = resultado.get(0);

        assertAll("Propiedades de la rutina",
                () -> assertEquals(5L,              r.getId()),
                () -> assertEquals("Brazo Completo", r.getNombre()),
                () -> assertEquals("Hipertrofia",    r.getObjetivo()),
                () -> assertEquals(4,                r.getDuracionSemanas())
        );

        Ejercicio ej = r.getEjercicios().get(0);
        assertAll("Propiedades del ejercicio",
                () -> assertEquals("Curl de bíceps", ej.getNombre()),
                () -> assertEquals(3,                 ej.getSeries()),
                () -> assertEquals(12,                ej.getRepeticiones()),
                () -> assertEquals("Bíceps",          ej.getGrupoMuscular())
        );

        verify(rutinaRepository, times(1)).findAll();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // CP-004 · Crear nueva rutina · Unitario
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("CP-004 – Crear nueva rutina: debe guardar y retornar la rutina con sus ejercicios")
    void cp004_crearNuevaRutina_debeGuardarYRetornarRutina() {

        // ── Given ─────────────────────────────────────────────────────────────
        Rutina rutinaInput = new Rutina();
        rutinaInput.setNombre("Cardio Funcional");
        rutinaInput.setObjetivo("Pérdida de grasa");
        rutinaInput.setDuracionSemanas(10);
        rutinaInput.setEjercicios(List.of(
                crearEjercicio(null, "Burpees",          3, 15, "Cardio"),
                crearEjercicio(null, "Mountain Climbers", 3, 20, "Abdomen"),
                crearEjercicio(null, "Jump Squats",       3, 12, "Piernas")
        ));

        Rutina rutinaGuardada = crearRutina(7L, "Cardio Funcional", "Pérdida de grasa", 10,
                List.of(
                        crearEjercicio(11L, "Burpees",          3, 15, "Cardio"),
                        crearEjercicio(12L, "Mountain Climbers", 3, 20, "Abdomen"),
                        crearEjercicio(13L, "Jump Squats",       3, 12, "Piernas")
                ));

        when(rutinaRepository.save(any(Rutina.class))).thenReturn(rutinaGuardada);

        // ── When ──────────────────────────────────────────────────────────────
        Rutina resultado = rutinaService.guardar(rutinaInput);

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado,                    "La rutina retornada no debe ser nula");
        assertEquals(7L,                            resultado.getId(),              "El ID generado debe ser 7");
        assertEquals("Cardio Funcional",            resultado.getNombre(),          "El nombre debe coincidir");
        assertEquals("Pérdida de grasa",            resultado.getObjetivo(),        "El objetivo debe coincidir");
        assertEquals(10,                            resultado.getDuracionSemanas(), "Las semanas deben ser 10");

        assertNotNull(resultado.getEjercicios(),    "La lista de ejercicios no debe ser nula");
        assertEquals(3,                             resultado.getEjercicios().size(),"Debe tener 3 ejercicios");

        assertEquals("Burpees",          resultado.getEjercicios().get(0).getNombre());
        assertEquals("Mountain Climbers",resultado.getEjercicios().get(1).getNombre());
        assertEquals("Jump Squats",      resultado.getEjercicios().get(2).getNombre());

        verify(rutinaRepository, times(1)).save(any(Rutina.class));
    }

    @Test
    @DisplayName("CP-004 – Crear nueva rutina: rutina sin ejercicios también debe guardarse")
    void cp004_crearRutinaSinEjercicios_debeGuardarCorrectamente() {

        // ── Given ─────────────────────────────────────────────────────────────
        Rutina rutinaInput = new Rutina();
        rutinaInput.setNombre("Plan Inicial");
        rutinaInput.setObjetivo("Adaptación");
        rutinaInput.setDuracionSemanas(4);
        rutinaInput.setEjercicios(List.of());

        Rutina guardada = crearRutina(8L, "Plan Inicial", "Adaptación", 4, List.of());

        when(rutinaRepository.save(any(Rutina.class))).thenReturn(guardada);

        // ── When ──────────────────────────────────────────────────────────────
        Rutina resultado = rutinaService.guardar(rutinaInput);

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado);
        assertEquals(8L,             resultado.getId());
        assertEquals("Plan Inicial", resultado.getNombre());
        assertTrue(resultado.getEjercicios().isEmpty(), "La lista de ejercicios debe estar vacía");

        verify(rutinaRepository, times(1)).save(any(Rutina.class));
    }

    @Test
    @DisplayName("CP-004 – Crear nueva rutina: debe delegar el guardado al repositorio exactamente una vez")
    void cp004_crearRutina_debeInvocarRepositorioUnaVez() {

        // ── Given ─────────────────────────────────────────────────────────────
        Rutina rutina = new Rutina();
        rutina.setNombre("Fuerza Máxima");
        rutina.setObjetivo("Fuerza");
        rutina.setDuracionSemanas(16);

        Rutina guardada = crearRutina(9L, "Fuerza Máxima", "Fuerza", 16, List.of());
        when(rutinaRepository.save(any(Rutina.class))).thenReturn(guardada);

        // ── When ──────────────────────────────────────────────────────────────
        rutinaService.guardar(rutina);

        // ── Then ──────────────────────────────────────────────────────────────
        verify(rutinaRepository, times(1)).save(any(Rutina.class));
        verifyNoMoreInteractions(rutinaRepository);
    }
}
