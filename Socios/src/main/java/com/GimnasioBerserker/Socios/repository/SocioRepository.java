package com.GimnasioBerserker.Socios.repository;

import com.GimnasioBerserker.Socios.Model.Socio;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Long>{
    @Query("SELECT e FROM Socio e WHERE e.estadoMembresia = true")
    List<Socio> findAllActivas();

    @Query("SELECT e FROM Socio e Where LOWER(e.rut) LIKE LOWER(CONCAT('%', :rut, '%'))")
    List<Socio> buscarPorRut(@Param("rut") String rut);

}
