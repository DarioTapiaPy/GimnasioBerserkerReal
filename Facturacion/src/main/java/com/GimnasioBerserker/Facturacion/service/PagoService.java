package com.GimnasioBerserker.Facturacion.service;

import com.GimnasioBerserker.Facturacion.model.Pago;

import com.GimnasioBerserker.Facturacion.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
