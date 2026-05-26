package com.GimnasioBerserker.Rutina.controller;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.service.RutinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
public class RutinaController {

    private final RutinaService rutinaService;

    @GetMapping
    public List<Rutina> obtenerTodas() {
        return rutinaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtenerPorId(@PathVariable Long id) {
        return rutinaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Rutina> buscarPorObjetivo(@RequestParam String objetivo) {
        return rutinaService.buscarPorObjetivo(objetivo);
    }

    @PostMapping
    public ResponseEntity<Rutina> crear(@Valid @RequestBody Rutina rutina) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rutinaService.guardar(rutina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rutina> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody Rutina rutina) {

        return rutinaService.actualizar(id, rutina)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}