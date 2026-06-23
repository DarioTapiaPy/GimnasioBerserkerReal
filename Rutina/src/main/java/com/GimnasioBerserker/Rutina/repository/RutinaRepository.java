package com.GimnasioBerserker.Rutina.repository;

import com.GimnasioBerserker.Rutina.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findBySocioId(Long socioId); // Método útil para la comunicación con el MS Socios
}