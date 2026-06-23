package com.GimnasioBerserker.Rutina.controller;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.service.RutinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@Tag(name = "Rutinas", description = "API para la gestión de rutinas de entrenamiento del Gimnasio Berserker")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Operation(summary = "Crear una nueva rutina", description = "Registra una rutina en la base de datos aplicando reglas de negocio de frecuencia semanal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rutina creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en las reglas de negocio (ej. días por semana inválidos)")
    })
    @PostMapping
    public ResponseEntity<?> crearRutina(@RequestBody Rutina rutina) {
        try {
            Rutina nuevaRutina = rutinaService.crearRutina(rutina);
            return new ResponseEntity<>(nuevaRutina, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Obtener todas las rutinas", description = "Retorna el listado completo de rutinas disponibles.")
    @GetMapping
    public ResponseEntity<List<Rutina>> listarRutinas() {
        return ResponseEntity.ok(rutinaService.obtenerTodas());
    }

    @Operation(summary = "Obtener rutina por ID", description = "Busca una rutina específica utilizando su identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtenerRutina(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rutinaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener rutinas por ID de Socio", description = "Retorna todas las rutinas asociadas a un socio en particular. Útil para comunicación entre microservicios.")
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<Rutina>> obtenerRutinasDeSocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(rutinaService.obtenerPorSocio(socioId));
    }
}