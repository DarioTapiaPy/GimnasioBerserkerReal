package com.GimnasioBerserker.Socios.service;

import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.client.RutinaClient;
import com.GimnasioBerserker.Socios.dto.RutinaResponseDTO;
import com.GimnasioBerserker.Socios.dto.SocioConRutinaDTO;
import com.GimnasioBerserker.Socios.repository.SocioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias y de integración para SocioService.
 *
 * CP-001 – Crear nuevo socio                      (Unitario)
 * CP-003 – Obtener socio con rutina               (Integración)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SocioService – Pruebas unitarias e integración")
class SocioServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @Mock
    private RutinaClient rutinaClient;

    @InjectMocks
    private SocioService socioService;

    // ═══════════════════════════════════════════════════════════════════════════
    // CP-001 · Crear nuevo socio · Unitario
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("CP-001 – Crear nuevo socio: debe guardar y retornar el socio correctamente")
    void cp001_crearNuevoSocio_debeGuardarYRetornarSocio() {

        // ── Given ─────────────────────────────────────────────────────────────
        Socio socioNuevo = new Socio();
        socioNuevo.setRut("12345678-9");
        socioNuevo.setNombre("Carlos Pérez");
        socioNuevo.setEmail("carlos.perez@gmail.com");
        socioNuevo.setEstadoMembresia(true);
        socioNuevo.setPlanId(1L);
        socioNuevo.setRutinaId(2L);

        Socio socioGuardado = new Socio();
        socioGuardado.setId(1L);
        socioGuardado.setRut("12345678-9");
        socioGuardado.setNombre("Carlos Pérez");
        socioGuardado.setEmail("carlos.perez@gmail.com");
        socioGuardado.setEstadoMembresia(true);
        socioGuardado.setPlanId(1L);
        socioGuardado.setRutinaId(2L);

        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        // ── When ──────────────────────────────────────────────────────────────
        Socio resultado = socioService.guardar(socioNuevo);

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado, "El socio retornado no debe ser nulo");
        assertEquals(1L,            resultado.getId(),            "El ID generado debe ser 1");
        assertEquals("12345678-9",  resultado.getRut(),           "El RUT debe coincidir");
        assertEquals("Carlos Pérez",resultado.getNombre(),        "El nombre debe coincidir");
        assertEquals("carlos.perez@gmail.com", resultado.getEmail(), "El email debe coincidir");
        assertTrue(resultado.isEstadoMembresia(),                 "La membresía debe estar activa");
        assertEquals(1L,            resultado.getPlanId(),        "El planId debe ser 1");
        assertEquals(2L,            resultado.getRutinaId(),      "El rutinaId debe ser 2");

        verify(socioRepository, times(1)).save(any(Socio.class));
    }

    @Test
    @DisplayName("CP-001 – Crear nuevo socio: socio con membresía inactiva también debe guardarse")
    void cp001_crearSocioInactivo_debeGuardarCorrectamente() {

        // ── Given ─────────────────────────────────────────────────────────────
        Socio socioInactivo = new Socio();
        socioInactivo.setRut("98765432-1");
        socioInactivo.setNombre("Ana Torres");
        socioInactivo.setEmail("ana.torres@gmail.com");
        socioInactivo.setEstadoMembresia(false);
        socioInactivo.setPlanId(2L);
        socioInactivo.setRutinaId(null);

        Socio guardado = new Socio();
        guardado.setId(2L);
        guardado.setRut("98765432-1");
        guardado.setNombre("Ana Torres");
        guardado.setEmail("ana.torres@gmail.com");
        guardado.setEstadoMembresia(false);
        guardado.setPlanId(2L);

        when(socioRepository.save(any(Socio.class))).thenReturn(guardado);

        // ── When ──────────────────────────────────────────────────────────────
        Socio resultado = socioService.guardar(socioInactivo);

        // ── Then ──────────────────────────────────────────────────────────────
        assertNotNull(resultado);
        assertFalse(resultado.isEstadoMembresia(), "La membresía debe estar inactiva");
        assertEquals("Ana Torres", resultado.getNombre());

        verify(socioRepository, times(1)).save(any(Socio.class));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // CP-003 · Obtener socio con rutina · Integración (SocioService + RutinaClient)
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("CP-003 – Obtener socio con rutina: debe integrar datos del socio y de la rutina remota")
    void cp003_obtenerSocioConRutina_debeRetornarDTOCompleto() {

        // ── Given ─────────────────────────────────────────────────────────────
        Long socioId = 1L;

        Socio socio = new Socio();
        socio.setId(socioId);
        socio.setRut("22665456-9");
        socio.setNombre("Roberto Medina");
        socio.setEmail("rob.medina@gmail.com");
        socio.setEstadoMembresia(true);
        socio.setPlanId(1L);
        socio.setRutinaId(1L);

        RutinaResponseDTO rutinaDTO = new RutinaResponseDTO();
        rutinaDTO.setId(1L);
        rutinaDTO.setNombre("Full Body");
        rutinaDTO.setObjetivo("Tonificación general");
        rutinaDTO.setDuracionSemanas(8);

        when(socioRepository.findById(socioId)).thenReturn(Optional.of(socio));
        when(rutinaClient.obtenerPorId(1L)).thenReturn(rutinaDTO);

        // ── When ──────────────────────────────────────────────────────────────
        Optional<SocioConRutinaDTO> resultado = socioService.obtenerSocioConRutina(socioId);

        // ── Then ──────────────────────────────────────────────────────────────
        assertTrue(resultado.isPresent(), "El resultado no debe ser vacío");

        SocioConRutinaDTO dto = resultado.get();
        assertEquals(socioId,           dto.getId(),             "El ID del socio debe coincidir");
        assertEquals("22665456-9",      dto.getRut(),            "El RUT debe coincidir");
        assertEquals("Roberto Medina",  dto.getNombre(),         "El nombre debe coincidir");
        assertEquals("rob.medina@gmail.com", dto.getEmail(),     "El email debe coincidir");
        assertEquals("Activa",          dto.getEstadoMembresia(),"La membresía activa debe decir 'Activa'");
        assertEquals(1L,                dto.getPlanId(),         "El planId debe coincidir");

        assertNotNull(dto.getRutina(),                           "La rutina no debe ser nula");
        assertEquals(1L,           dto.getRutina().getId(),      "El ID de la rutina debe ser 1");
        assertEquals("Full Body",  dto.getRutina().getNombre(),  "El nombre de la rutina debe coincidir");
        assertEquals(8,            dto.getRutina().getDuracionSemanas(), "Las semanas deben coincidir");

        verify(socioRepository, times(1)).findById(socioId);
        verify(rutinaClient,    times(1)).obtenerPorId(1L);
    }

    @Test
    @DisplayName("CP-003 – Obtener socio con rutina: socio sin rutina asignada debe retornar DTO con rutina null")
    void cp003_obtenerSocioSinRutinaAsignada_rutinaDebeSerNull() {

        // ── Given ─────────────────────────────────────────────────────────────
        Long socioId = 2L;

        Socio socio = new Socio();
        socio.setId(socioId);
        socio.setRut("21802504-8");
        socio.setNombre("Julia González");
        socio.setEmail("jul.gonzalez@gmail.com");
        socio.setEstadoMembresia(false);
        socio.setPlanId(3L);
        socio.setRutinaId(null);          // sin rutina asignada

        when(socioRepository.findById(socioId)).thenReturn(Optional.of(socio));

        // ── When ──────────────────────────────────────────────────────────────
        Optional<SocioConRutinaDTO> resultado = socioService.obtenerSocioConRutina(socioId);

        // ── Then ──────────────────────────────────────────────────────────────
        assertTrue(resultado.isPresent());
        SocioConRutinaDTO dto = resultado.get();

        assertNull(dto.getRutina(),    "Si el socio no tiene rutinaId, la rutina debe ser null");
        assertEquals("Inactiva",       dto.getEstadoMembresia(), "Membresía inactiva debe decir 'Inactiva'");

        verify(socioRepository, times(1)).findById(socioId);
        // No debe llamar al cliente de Rutinas si el socio no tiene rutinaId
        verify(rutinaClient, never()).obtenerPorId(any());
    }

    @Test
    @DisplayName("CP-003 – Obtener socio con rutina: ID inexistente debe retornar Optional vacío")
    void cp003_socioNoExistente_debeRetornarVacio() {

        // ── Given ─────────────────────────────────────────────────────────────
        Long idInexistente = 999L;
        when(socioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // ── When ──────────────────────────────────────────────────────────────
        Optional<SocioConRutinaDTO> resultado = socioService.obtenerSocioConRutina(idInexistente);

        // ── Then ──────────────────────────────────────────────────────────────
        assertFalse(resultado.isPresent(), "Debe retornar Optional vacío para un ID inexistente");

        verify(socioRepository, times(1)).findById(idInexistente);
        verify(rutinaClient, never()).obtenerPorId(any());
    }
}
