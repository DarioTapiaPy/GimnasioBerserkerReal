package com.GimnasioBerserker.Rutina.service;

import com.GimnasioBerserker.Rutina.model.Rutina;
import com.GimnasioBerserker.Rutina.repository.RutinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public List<Rutina> obtenerTodas() {
        return rutinaRepository.findAll();
    }

    public Optional<Rutina> obtenerPorId(Long id) {
        return rutinaRepository.findById(id);
    }

    public List<Rutina> buscarPorObjetivo(String objetivo) {
        return rutinaRepository.findByObjetivoContainingIgnoreCase(objetivo);
    }

    public Rutina guardar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    public Optional<Rutina> actualizar(Long id, Rutina datos) {
        return rutinaRepository.findById(id).map(r -> {
            r.setNombre(datos.getNombre());
            r.setObjetivo(datos.getObjetivo());
            r.setDuracionSemanas(datos.getDuracionSemanas());
            r.setEjercicios(datos.getEjercicios());
            return rutinaRepository.save(r);
        });
    }

    public void eliminar(Long id) {
        rutinaRepository.deleteById(id);
    }
}
