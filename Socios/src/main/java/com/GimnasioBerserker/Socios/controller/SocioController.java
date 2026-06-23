package com.GimnasioBerserker.Socios.controller;

import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.dto.SocioConRutinaDTO;
import com.GimnasioBerserker.Socios.service.SocioService;
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

@Tag(name = "Socios", description = "Gestión de socios y membresías del Gimnasio Berserker")
@RestController
@RequestMapping("/api/socios")
@RequiredArgsConstructor
public class SocioController {

    private final SocioService socioService;

    // ── GET /api/socios ────────────────────────────────────────────────────────

    @Operation(
            summary     = "Listar todos los socios",
            description = "Retorna la lista completa de socios registrados, sin importar su estado de membresía."
    )
    @ApiResponse(
            responseCode = "200",
            description  = "Lista de socios obtenida correctamente",
            content      = @Content(array = @ArraySchema(schema = @Schema(implementation = Socio.class)))
    )
    @GetMapping
    public List<Socio> obtenerTodas() {
        return socioService.obtenerTodas();
    }

    // ── GET /api/socios/activas ────────────────────────────────────────────────

    @Operation(
            summary     = "Listar socios con membresía activa",
            description = "Retorna únicamente los socios cuyo campo estadoMembresia es true."
    )
    @ApiResponse(
            responseCode = "200",
            description  = "Lista de socios activos obtenida correctamente",
            content      = @Content(array = @ArraySchema(schema = @Schema(implementation = Socio.class)))
    )
    @GetMapping("/activas")
    public List<Socio> obtenerActivas() {
        return socioService.obtenerActivas();
    }

    // ── GET /api/socios/buscar?rut=... ─────────────────────────────────────────

    @Operation(
            summary     = "Buscar socios por RUT",
            description = "Realiza una búsqueda parcial e insensible a mayúsculas sobre el campo RUT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Socio.class)))),
            @ApiResponse(responseCode = "400", description = "Parámetro 'rut' ausente o inválido")
    })
    @GetMapping("/buscar")
    public List<Socio> buscar(
            @Parameter(description = "Fragmento o valor completo del RUT a buscar", required = true, example = "22665456-9")
            @RequestParam String rut) {
        return socioService.buscarPorRut(rut);
    }

    // ── GET /api/socios/{id} ───────────────────────────────────────────────────

    @Operation(
            summary     = "Obtener socio por ID",
            description = "Busca y retorna un socio según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Socio encontrado",
                    content = @Content(schema = @Schema(implementation = Socio.class))),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerPorId(
            @Parameter(description = "ID único del socio", required = true, example = "1")
            @PathVariable Long id) {
        return socioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/socios ───────────────────────────────────────────────────────

    @Operation(
            summary     = "Crear un nuevo socio",
            description = "Registra un nuevo socio en el sistema. El RUT y el email deben ser únicos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Socio creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Socio.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Socio> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo socio", required = true,
                    content = @Content(schema = @Schema(implementation = Socio.class)))
            @Valid @RequestBody Socio socio) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(socioService.guardar(socio));
    }

    // ── PUT /api/socios/{id} ───────────────────────────────────────────────────

    @Operation(
            summary     = "Actualizar socio existente",
            description = "Actualiza los datos de un socio ya registrado. Retorna 404 si el socio no existe."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Socio actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Socio.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizar(
            @Parameter(description = "ID del socio a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Socio datos) {
        return socioService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/socios/{id} ────────────────────────────────────────────────

    @Operation(
            summary     = "Eliminar un socio",
            description = "Elimina permanentemente el registro de un socio por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Socio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del socio a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ── GET /api/socios/{id}/rutina ────────────────────────────────────────────

    @Operation(
            summary     = "Obtener socio con su rutina asignada",
            description = """
                    Retorna la información completa del socio junto con la rutina que tiene asignada.
                    Realiza una llamada REST al microservicio de Rutinas para obtener los detalles.
                    Si el socio no tiene rutina asignada, el campo 'rutina' será null.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Socio y rutina obtenidos correctamente",
                    content = @Content(schema = @Schema(implementation = SocioConRutinaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado"),
            @ApiResponse(responseCode = "503", description = "Microservicio de Rutinas no disponible")
    })
    @GetMapping("/{id}/rutina")
    public ResponseEntity<SocioConRutinaDTO> obtenerConRutina(
            @Parameter(description = "ID del socio", required = true, example = "1")
            @PathVariable Long id) {
        return socioService.obtenerSocioConRutina(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
