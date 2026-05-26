package com.GimnasioBerserker.Inventario.Service;

import com.GimnasioBerserker.Inventario.Model.Inventario;
import com.GimnasioBerserker.Inventario.Repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository repository;

    @Override
    public List<Inventario> listarTodos(){
        return repository.findAll();
    }

    @Override
    public Optional<Inventario> buscarPorId(Long id){
        return repository.findById(id);
    }

    @Override
    public Inventario guardar (Inventario inventario){
        if (inventario.getProximaMantencion().isBefore(inventario.getUltimaMantencion())){
            throw new IllegalArgumentException("La fecha de proxima mantencion no puede ser anterior a la fecha de la ultima mantencion.");
        }
        return repository.save(inventario);
    }

    @Override
    public void eliminar(Long id){
        repository.deleteById(id);
    }





}
