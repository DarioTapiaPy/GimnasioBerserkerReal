package com.GimnasioBerserker.Rutina.service;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.repository.RutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;

    public Rutina crearRutina(Rutina rutina) {
        // Regla de Negocio 1: Una rutina debe tener entre 1 y 7 días
        if (rutina.getDiasPorSemana() == null || rutina.getDiasPorSemana() < 1 || rutina.getDiasPorSemana() > 7) {
            throw new IllegalArgumentException("La rutina debe tener una frecuencia de 1 a 7 días por semana.");
        }

        // Regla de Negocio 2: Validar que el nombre no venga vacío
        if (rutina.getNombre() == null || rutina.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la rutina es obligatorio.");
        }

        return rutinaRepository.save(rutina);
    }

    public List<Rutina> obtenerTodas() {
        return rutinaRepository.findAll();
    }

    public Rutina obtenerPorId(Long id) {
        return rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + id));
    }

    public List<Rutina> obtenerPorSocio(Long socioId) {
        return rutinaRepository.findBySocioId(socioId);
    }
}