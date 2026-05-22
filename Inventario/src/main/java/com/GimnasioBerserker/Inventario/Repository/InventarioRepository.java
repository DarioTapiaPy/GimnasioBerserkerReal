package com.GimnasioBerserker.Inventario.Repository;

import com.GimnasioBerserker.Inventario.Model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
