package com.GimnasioBerserker.Socios.service;

import com.GimnasioBerserker.Socios.Model.Socio;
import com.GimnasioBerserker.Socios.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SocioService {
    private final SocioRepository socioRepository;

    public SocioRepository getSocioRepository() {
        return socioRepository;
    }

    public List<Socio> obtenerTodas(){ return socioRepository.findAll();}

    public List<Socio> obtenerActivas(){ return socioRepository.findAllActivas();}

    public List<Socio> buscarPorRut(String rut){ return socioRepository.buscarPorRut(rut);}

    public Optional<Socio> obtenerPorId(Long id){ return socioRepository.findById(id);}

    public Socio guardar(Socio socio) { return socioRepository.save(socio);}

    public Optional<Socio> actualizar(Long id, Socio datos){
        return socioRepository.findById(id).map(e ->{
            e.setNombre(datos.getNombre());
            e.setRut(datos.getRut());
            e.setEmail(datos.getEmail());
            e.setPlanId(datos.getPlanId());
            e.setEstadoMembresia(datos.isEstadoMembresia());
            return socioRepository.save(e);
        });
    }

    public void eliminar(Long id){ socioRepository.deleteById(id);}

}
