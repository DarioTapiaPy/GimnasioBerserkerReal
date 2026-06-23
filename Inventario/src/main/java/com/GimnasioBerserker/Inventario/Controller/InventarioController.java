package com.GimnasioBerserker.Inventario.Controller;

import com.GimnasioBerserker.Inventario.Model.Inventario;
import com.GimnasioBerserker.Inventario.Service.InventarioService;
import com.GimnasioBerserker.Inventario.Dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
@Tag(name = "Inventario Controller", description = "Operaciones de gestión del inventario de máquinas del gimnasio")
public class InventarioController {

    @Autowired
    private InventarioService service;

    //lista todos
    @GetMapping
    @Operation(summary = "Obtener todas las máquinas", description = "Retorna una lista completa de las máquinas registradas en el inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public List<Inventario> listar() {
        return service.listarTodos();
    }

    //buscar por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una máquina por ID", description = "Busca y retorna el detalle de una máquina específica mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Máquina encontrada con éxito"),
            @ApiResponse(responseCode = "404", description = "Máquina no encontrada en el inventario",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Inventario> inventarioOpt = service.buscarPorId(id);
        if (inventarioOpt.isPresent()) {
            return ResponseEntity.ok(inventarioOpt.get());
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("Máquina con ID " + id + " no encontrada en el inventario", 404));
        }
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva máquina", description = "Crea un registro de máquina en el inventario controlando reglas de negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Máquina registrada con éxito"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados o regla de negocio violada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> crear(@RequestBody Inventario inventario) {
        try {
            Inventario guardado = service.guardar(inventario);
            return ResponseEntity.status(201).body(guardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(new ErrorResponse("Regla de negocio: " + e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ErrorResponse("Error al registrar máquina: " + e.getMessage(), 400));
        }
    }

    // Actualizar por id
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una máquina existente", description = "Modifica los datos de una máquina mediante su ID y valida las fechas de mantención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Máquina actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de validación de fechas",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "ID no encontrado para actualizar",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Inventario detalles) {
        Optional<Inventario> inventarioOpt = service.buscarPorId(id);

        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setNombreMaquina(detalles.getNombreMaquina());
            inventario.setTipo(detalles.getTipo());
            inventario.setEstado(detalles.getEstado());
            inventario.setUltimaMantencion(detalles.getUltimaMantencion());
            inventario.setProximaMantencion(detalles.getProximaMantencion());

            try {
                return ResponseEntity.ok(service.guardar(inventario));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400)
                        .body(new ErrorResponse("Error de fechas: " + e.getMessage(), 400));
            }
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("No se encontró el ID " + id + " para actualizar", 404));
        }
    }

    // Eliminar por id
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una máquina por ID", description = "Elimina físicamente el registro de la máquina del inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de inventario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontró la máquina para eliminar",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Inventario> inventarioOpt = service.buscarPorId(id);
        if (inventarioOpt.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.ok("Registro de inventario eliminado con éxito");
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("No se encontró la máquina para eliminar", 404));
        }
    }
}