package com.GimnasioBerserker.Empleados.Controller;

import com.GimnasioBerserker.Empleados.Model.Empleado;
import com.GimnasioBerserker.Empleados.Service.EmpleadoService;
import com.GimnasioBerserker.Empleados.Dto.ErrorResponse; // Tu DTO
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController     //convierte los objetos en formato json leible para el navegador
@RequestMapping("/api/empleados")//ruta

@Tag(name = "Empleado Controller", description = "Operaciones de gestion de empleados")

public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping //lista todos los empleados de la bd
    public List<Empleado> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{idEmp}") //lista los empleados buscador por id
    @Operation(summary = "Obtener un empleado por ID", description = "Empleado encontrado exitosamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
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
    @Operation(summary = "Crear un nuevo empleado", description = "Registrar un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
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
    @Operation(summary = "Actualizar un empleado existente",description = "Modificar los datos de un empleado buscando por ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontro el ID para el empleado solicitado")

    })
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
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado solicitando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Prohibido eliminar a un Jefe"),
            @ApiResponse(responseCode = "404", description = "No se encontro el empleado ")
    })
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