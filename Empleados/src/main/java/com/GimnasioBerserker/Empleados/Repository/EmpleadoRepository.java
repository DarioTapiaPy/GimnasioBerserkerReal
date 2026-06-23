package com.GimnasioBerserker.Empleados.Repository;

import com.GimnasioBerserker.Empleados.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

}
