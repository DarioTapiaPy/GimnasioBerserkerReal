package com.GimnasioBerserker.Empleados.Service;

import com.GimnasioBerserker.Empleados.Model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    List<Empleado> listarTodos();
    Optional<Empleado> buscarPorId(Long idEmp);
    Empleado guardar (Empleado empleado);
    void eliminar(Long idEmp);
}
