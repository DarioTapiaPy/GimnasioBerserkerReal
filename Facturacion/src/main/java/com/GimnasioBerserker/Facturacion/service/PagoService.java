package com.GimnasioBerserker.Facturacion.service;

import com.GimnasioBerserker.Facturacion.dto.PagoFacturaDTO;
import com.GimnasioBerserker.Facturacion.model.Pago;
import com.GimnasioBerserker.Facturacion.dto.PagoDto;
import com.GimnasioBerserker.Facturacion.model.Factura;

import com.GimnasioBerserker.Facturacion.repository.FacturaRepository;
import com.GimnasioBerserker.Facturacion.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    public Pago findById(Long id){
        return pagoRepository.findById(id).orElse(null);


    }
    public Pago guardarPago(Pago pago){
        return pagoRepository.save(pago);
    }
    public Pago actualizarPago(long id, Pago pago){
        Pago pagoExistente = pagoRepository.findById(id).orElse(null);
        if (pagoExistente != null){
            pagoExistente.setFacturaId(pago.getFacturaId());
            pagoExistente.setFechaPago(pago.getFechaPago());
            pagoExistente.setMetodoPago(pago.getMetodoPago());
            pagoExistente.setMontoPago(pago.getMontoPago());

            return  pagoRepository.save(pagoExistente);

        }
        return null;

    }
    public void eliminarPago(Long id){
        pagoRepository.deleteById(id);
    }


    @Autowired
    private FacturaRepository facturaRepository;
    public PagoFacturaDTO obtenerPagoConFactura(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);

        if (pago == null){
            return null;
        }
        Factura factura = facturaRepository.findById(pago.getFacturaId()).orElse(null);
        if (factura == null){

        }
        PagoFacturaDTO dto = new PagoFacturaDTO();
        dto.setFacturaId(pago.getId());
        dto.setFacturaId(pago.getFacturaId());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMontoPago(pago.getMontoPago());
        dto.setMetodoPago(pago.getMetodoPago());

        dto.setIdSocio(factura.getIdSocio());
        dto.setValor(factura.getValor());
        dto.setFecha_facturacion(factura.getFecha_facturacion());

        return dto;
    }
}
