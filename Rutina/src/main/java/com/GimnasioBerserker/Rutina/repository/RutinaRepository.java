package com.GimnasioBerserker.Rutina.repository;

import com.GimnasioBerserker.Rutina.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    List<Rutina> findByObjetivoContainingIgnoreCase(String objetivo);
}
