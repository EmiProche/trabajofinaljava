package com.proyectofinal.FacturacionEntregaFinalProchetto.repository;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
