package com.GimnasioBerserker.Inventario.Service;

import com.GimnasioBerserker.Inventario.Model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioService {
    List<Inventario> listarTodos();
    Optional<Inventario> buscarPorId(Long id);
    Inventario guardar(Inventario inventario);
    void eliminar(Long id);

}
