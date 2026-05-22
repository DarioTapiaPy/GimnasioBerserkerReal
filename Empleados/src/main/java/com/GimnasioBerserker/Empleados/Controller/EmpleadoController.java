package com.GimnasioBerserker.Empleados.Controller;

import com.GimnasioBerserker.Empleados.Model.Empleado;
import com.GimnasioBerserker.Empleados.Service.EmpleadoService;
import com.GimnasioBerserker.Empleados.Dto.ErrorResponse; // Tu DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController     //convierte los objetos en formato json leible para el navegador
@RequestMapping("/api/empleados")   //ruta

public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping //lista todos los empleados de la bd
    public List<Empleado> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{idEmp}") //lista los empleados buscador por id
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idEmp) {
        Optional<Empleado> empleadoOpt = service.buscarPorId(idEmp);
        if (empleadoOpt.isPresent()) {
            return ResponseEntity.ok(empleadoOpt.get()); //en caso que funcione 200 OK
        } else {
            return ResponseEntity.status(404)   //en caso q no funcione
                    .body(new ErrorResponse("Empleado con ID " + idEmp + " no encontrado", 404));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Empleado empleado) {
        try {
            Empleado guardado = service.guardar(empleado);
            return ResponseEntity.status(201).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ErrorResponse("Error al crear: " + e.getMessage(), 400));
        }
    }

    @PutMapping("/{idEmp}")
    public ResponseEntity<?> actualizar(@PathVariable Long idEmp, @RequestBody Empleado detalles) {
        Optional<Empleado> empleadoOpt = service.buscarPorId(idEmp);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.setRunEmp(detalles.getRunEmp());
            empleado.setNombreEmp(detalles.getNombreEmp());
            empleado.setCargoEmp(detalles.getCargoEmp());
            empleado.setEspecialidadEmp(detalles.getEspecialidadEmp());
            empleado.setSueldoEmp(detalles.getSueldoEmp());

            return ResponseEntity.ok(service.guardar(empleado));
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("No se encontró el ID " + idEmp + " para actualizar", 404));
        }
    }



    @DeleteMapping("/{idEmp}") //<?> hace que el metodo sea flexible
    public ResponseEntity<?> eliminar(@PathVariable Long idEmp) {
        Optional<Empleado> empleadoOpt = service.buscarPorId(idEmp);
        if (empleadoOpt.isPresent()) {
            if ("Jefe".equalsIgnoreCase(empleadoOpt.get().getCargoEmp())) {
                return ResponseEntity.status(403)   //protejemos al jefe en caso de querer eliminar
                        .body(new ErrorResponse("No se puede eliminar a un Jefe", 403));
            }
            service.eliminar(idEmp);
            return ResponseEntity.ok("Empleado eliminado con éxito");
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("No se encontró el empleado", 404));
        }
    }
}