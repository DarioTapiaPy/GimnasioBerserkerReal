package com.GimnasioBerserker.Comercial.client;

import com.GimnasioBerserker.Comercial.dto.FacturaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "facturacion-client", url = "http://facturacion-service:8084")
public interface Facturacionclient {

    @PostMapping("/api/facturacion/facturas")
    Object crearFactura(@RequestBody FacturaRequestDTO facturaRequestDTO);
}
