package com.GimnasioBerserker.Facturacion.repository;

import com.GimnasioBerserker.Facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface FacturaRepository extends JpaRepository<Factura, Long> {

}