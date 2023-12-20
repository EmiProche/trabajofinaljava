package com.proyectofinal.FacturacionEntregaFinalProchetto.repository;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Cliente;
import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    @Transactional
    void deleteByCliente(Cliente cliente);

}
