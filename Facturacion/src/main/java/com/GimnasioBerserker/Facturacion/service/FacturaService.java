package com.GimnasioBerserker.Facturacion.service;

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
            FacturaExistente.setCliente(factura.getCliente());
            FacturaExistente.setRut(factura.getRut());
            FacturaExistente.setTipoMembresia(factura.getTipoMembresia());
            FacturaExistente.setFecha_facturacion(LocalDateTime.now());
            FacturaExistente.setValor(factura.getValor());

            return facturaRepository.save(FacturaExistente);

        }
        return null;

    }
    public void  eliminarFactura(long id){
        facturaRepository.deleteById(id);
    }
}
