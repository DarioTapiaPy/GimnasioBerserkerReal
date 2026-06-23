package com.GimnasioBerserker.Facturacion.repository;


import com.GimnasioBerserker.Facturacion.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}