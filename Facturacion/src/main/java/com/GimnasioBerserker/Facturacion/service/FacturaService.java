package com.GimnasioBerserker.Facturacion.service;

import com.GimnasioBerserker.Facturacion.client.SocioClient;
import com.GimnasioBerserker.Facturacion.dto.FacturaSocioDTO;
import com.GimnasioBerserker.Facturacion.dto.SocioDTO;
import com.GimnasioBerserker.Facturacion.model.Factura;
import com.GimnasioBerserker.Facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura>ListarFacturas(){
        return facturaRepository.findAll();
    }

     public Factura findById(Long id) {
        return facturaRepository.findById(id).orElse(null);
     }

    public Factura guardarFactura(Factura factura){
        return facturaRepository.save(factura);
    }


    public Factura actualizarFactura(long id, Factura factura ){
        Factura FacturaExistente = facturaRepository.findById(id).orElse(null);
        if(FacturaExistente != null){
            FacturaExistente.setIdSocio(factura.getIdSocio());
            FacturaExistente.setFecha_facturacion(LocalDateTime.now());
            FacturaExistente.setValor(factura.getValor());

            return facturaRepository.save(FacturaExistente);

        }
        return null;

    }
    public void  eliminarFactura(long id){
        facturaRepository.deleteById(id);
    }


    @Autowired
    SocioClient socioClient;

    public SocioDTO obtenerSocioDeFactura(Long facturaId) {

        Factura factura = facturaRepository.findById(facturaId).orElse(null);

        if (factura == null) {
            return null;
        }

        return socioClient.obtenerSocioPorId(factura.getIdSocio());
    }

    public FacturaSocioDTO obtenerFacturaConSocio (long id ){
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura == null) {
            return null;
        }
        SocioDTO socio = socioClient.obtenerSocioPorId(factura.getIdSocio());
        FacturaSocioDTO dto = new FacturaSocioDTO();
        dto.setId(factura.getId());
        dto.setIdSocio(factura.getIdSocio());
        dto.setValor(factura.getValor());
        dto.setFecha_facturacion(factura.getFecha_facturacion());

        dto.setRut(socio.getRut());
        dto.setNombre(socio.getNombre());
        dto.setEmail(socio.getEmail());
        dto.setEstadoMembresia(socio.isEstadoMembresia());
        dto.setPlanId(socio.getPlanId());
        return dto;
    }
}
