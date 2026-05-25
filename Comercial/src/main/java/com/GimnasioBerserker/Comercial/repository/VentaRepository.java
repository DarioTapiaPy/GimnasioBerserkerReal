package com.GimnasioBerserker.Comercial.repository;

import com.GimnasioBerserker.Comercial.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long> {
}