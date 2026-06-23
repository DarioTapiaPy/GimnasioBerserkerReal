package com.GimnasioBerserker.Socios.controller;


import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.dto.SocioConRutinaDTO;
import com.GimnasioBerserker.Socios.service.SocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
@RequiredArgsConstructor

public class SocioController {

    private final SocioService socioService;

    @GetMapping
    public List<Socio> obtenerTodas(){ return socioService.obtenerTodas();}

    @GetMapping("/activas")
    public List<Socio> obtenerActivas(){ return socioService.obtenerActivas();}

    @GetMapping("/buscar")
    public List<Socio> buscar(@RequestParam String rut){ return socioService.buscarPorRut(rut);}

    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerPorId(@PathVariable Long id){
        return socioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Socio> crear(@Valid @RequestBody Socio socio){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(socioService.guardar(socio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizar(@PathVariable Long id, @Valid @RequestBody Socio datos){
        return socioService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/rutina")
    public ResponseEntity<SocioConRutinaDTO> obtenerConRutina(@PathVariable Long id) {
        return socioService.obtenerSocioConRutina(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
