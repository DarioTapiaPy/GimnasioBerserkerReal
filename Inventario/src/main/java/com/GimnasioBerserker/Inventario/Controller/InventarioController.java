package com.GimnasioBerserker.Inventario.Controller;

import com.GimnasioBerserker.Inventario.Model.Inventario;
import com.GimnasioBerserker.Inventario.Service.InventarioService;
import com.GimnasioBerserker.Inventario.Dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService service;

    //lista todos
    @GetMapping
    public List<Inventario> listar() {
        return service.listarTodos();
    }

    //buscar por id
    @GetMapping("/{id}")
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