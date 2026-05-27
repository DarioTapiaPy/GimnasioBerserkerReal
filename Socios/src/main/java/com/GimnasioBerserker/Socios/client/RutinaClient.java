package com.GimnasioBerserker.Socios.client;

import com.GimnasioBerserker.Socios.dto.RutinaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rutina-service", url = "${rutina.service.url}")
public interface RutinaClient {

    @GetMapping("/api/rutinas/{id}")
    RutinaResponseDTO obtenerPorId(@PathVariable("id") Long id);
}