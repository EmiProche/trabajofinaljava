package com.proyectofinal.FacturacionEntregaFinalProchetto.repository;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
