package com.GimnasioBerserker.Rutina.controller;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.service.RutinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rutinas", description = "Gestión de rutinas de entrenamiento del Gimnasio Berserker")
@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
public class RutinaController {

    private final RutinaService rutinaService;

    // ── GET /api/rutinas ───────────────────────────────────────────────────────

    @Operation(
            summary     = "Listar todas las rutinas",
            description = "Retorna la lista completa de rutinas de entrenamiento registradas, incluyendo sus ejercicios."
    )
    @ApiResponse(
            responseCode = "200",
            description  = "Lista de rutinas obtenida correctamente",
            content      = @Content(array = @ArraySchema(schema = @Schema(implementation = Rutina.class)))
    )
    @GetMapping
    public List<Rutina> obtenerTodas() {
        return rutinaService.obtenerTodas();
    }

    // ── GET /api/rutinas/{id} ──────────────────────────────────────────────────

    @Operation(
            summary     = "Obtener rutina por ID",
            description = "Busca y retorna una rutina específica junto con todos sus ejercicios."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rutina encontrada",
                    content = @Content(schema = @Schema(implementation = Rutina.class))),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtenerPorId(
            @Parameter(description = "ID único de la rutina", required = true, example = "1")
            @PathVariable Long id) {
        return rutinaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET /api/rutinas/buscar?objetivo=... ───────────────────────────────────

    @Operation(
            summary     = "Buscar rutinas por objetivo",
            description = "Realiza una búsqueda parcial e insensible a mayúsculas sobre el campo 'objetivo'."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Rutina.class)))),
            @ApiResponse(responseCode = "400", description = "Parámetro 'objetivo' ausente o inválido")
    })
    @GetMapping("/buscar")
    public List<Rutina> buscarPorObjetivo(
            @Parameter(description = "Objetivo de entrenamiento a buscar", required = true, example = "Fuerza")
            @RequestParam String objetivo) {
        return rutinaService.buscarPorObjetivo(objetivo);
    }

    // ── POST /api/rutinas ──────────────────────────────────────────────────────

    @Operation(
            summary     = "Crear una nueva rutina",
            description = "Registra una nueva rutina de entrenamiento con sus ejercicios asociados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rutina creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Rutina.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (nombre obligatorio)")
    })
    @PostMapping
    public ResponseEntity<Rutina> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva rutina incluyendo ejercicios", required = true,
                    content = @Content(schema = @Schema(implementation = Rutina.class)))
            @Valid @RequestBody Rutina rutina) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rutinaService.guardar(rutina));
    }

    // ── PUT /api/rutinas/{id} ──────────────────────────────────────────────────

    @Operation(
            summary     = "Actualizar rutina existente",
            description = "Actualiza los datos de una rutina ya registrada, incluyendo sus ejercicios."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rutina actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Rutina.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Rutina> actualizar(
            @Parameter(description = "ID de la rutina a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Rutina rutina) {
        return rutinaService.actualizar(id, rutina)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/rutinas/{id} ───────────────────────────────────────────────

    @Operation(
            summary     = "Eliminar una rutina",
            description = "Elimina permanentemente una rutina y sus ejercicios asociados por ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rutina eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la rutina a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        rutinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
