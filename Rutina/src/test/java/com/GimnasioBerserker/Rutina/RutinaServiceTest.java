package com.GimnasioBerserker.Rutina;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.repository.RutinaRepository;
import com.GimnasioBerserker.Rutina.service.RutinaService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RutinaServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RutinaService rutinaService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializamos DataFaker antes de cada prueba
        faker = new Faker();
    }

    @Test
    @DisplayName("Obtener todas las rutinas (Listar)")
    void listarTodasLasRutinas() {
        // 1. GIVEN (Dado que tengo 2 rutinas en la base de datos)
        Rutina rutina1 = new Rutina();
        rutina1.setId(1L);
        rutina1.setNombre(faker.verb().ingForm() + " Workout"); // Genera un nombre aleatorio

        Rutina rutina2 = new Rutina();
        rutina2.setId(2L);
        rutina2.setNombre(faker.verb().ingForm() + " Routine"); // Genera otro nombre aleatorio

        // Le decimos al mock del repositorio que devuelva esta lista cuando llamen a findAll()
        when(rutinaRepository.findAll()).thenReturn(List.of(rutina1, rutina2));

        // 2. WHEN (Cuando ejecuto el método en el Service)
        List<Rutina> resultado = rutinaService.obtenerTodas();

        // 3. THEN (Entonces verifico que el resultado sea el esperado)
        assertNotNull(resultado); // Verifico que no sea nulo
        assertEquals(2, resultado.size()); // Verifico que traiga exactamente 2 elementos
        verify(rutinaRepository, times(1)).findAll(); // Verifico que se llamó a la BD una vez
    }

    @Test
    @DisplayName("Crear una nueva rutina")
    void crearNuevaRutina() {
        // 1. GIVEN (Dado que un usuario envía una rutina válida)
        Rutina rutinaInput = new Rutina();
        rutinaInput.setNombre("Rutina Fuerza " + faker.name().firstName());
        rutinaInput.setNivel("Principiante");
        rutinaInput.setDiasPorSemana(3); // Regla de negocio: entre 1 y 7
        rutinaInput.setSocioId(10L);

        // Simulamos la rutina que la base de datos devolverá (con su ID ya generado)
        Rutina rutinaGuardada = rutinaInput;
        rutinaGuardada.setId(1L);

        when(rutinaRepository.save(any(Rutina.class))).thenReturn(rutinaGuardada);

        // 2. WHEN (Cuando intento crearla en el Service)
        Rutina resultado = rutinaService.crearRutina(rutinaInput);

        // 3. THEN (Entonces verifico que se haya guardado con el ID asignado)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); // Confirmo que se le asignó el ID 1
        assertEquals("Principiante", resultado.getNivel());
        verify(rutinaRepository, times(1)).save(rutinaInput); // Confirmo que se intentó guardar 1 vez
    }
}