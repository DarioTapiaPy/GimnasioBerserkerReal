package com.GimnasioBerserker.Facturacion.client;


import com.GimnasioBerserker.Facturacion.dto.SocioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "socios-service" ,  url = "http://localhost:8082")
public interface SocioClient {
    @GetMapping ("/api/socios/{id}")
    SocioDTO obtenerSocioPorId(@PathVariable long id );

}
