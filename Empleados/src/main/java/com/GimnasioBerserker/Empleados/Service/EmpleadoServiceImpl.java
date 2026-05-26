package com.GimnasioBerserker.Empleados.Service;

import com.GimnasioBerserker.Empleados.Model.Empleado;
import com.GimnasioBerserker.Empleados.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository repository;

    @Override
    public List<Empleado> listarTodos(){
        return repository.findAll();
    }

    @Override
    public Optional<Empleado> buscarPorId(Long idEmp){
        return repository.findById(idEmp);
    }

    @Override
    public Empleado guardar (Empleado empleado){        //bono por especialidad "Pesas" de 10%
        if (empleado.getEspecialidadEmp() != null
        && empleado.getEspecialidadEmp().equalsIgnoreCase("Pesas")){
            BigDecimal sueldoActual = empleado.getSueldoEmp();
            BigDecimal bono = sueldoActual.multiply(new BigDecimal("0.10"));
            empleado.setSueldoEmp(sueldoActual.add(bono));
            System.out.println("Se aplico un bono del 10% a " + empleado.getNombreEmp());
        }
        return repository.save(empleado);

    }
    @Override
    public void eliminar (Long idEmp){
        repository.deleteById(idEmp);
    }
}
