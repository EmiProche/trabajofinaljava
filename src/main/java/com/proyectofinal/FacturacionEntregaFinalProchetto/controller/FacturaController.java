package com.proyectofinal.FacturacionEntregaFinalProchetto.controller;

import com.proyectofinal.FacturacionEntregaFinalProchetto.dto.FacturaDTO;
import com.proyectofinal.FacturacionEntregaFinalProchetto.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="final/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    //Crear factura
    @PostMapping
    public ResponseEntity<FacturaDTO> createFactura(@RequestBody FacturaDTO facturaDTO) {
        FacturaDTO nuevaFactura = facturaService.createFactura(facturaDTO);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

    //Listar todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturaService.getAllFacturas();
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    //Obtener factura por id
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Integer id) {
        FacturaDTO factura = facturaService.getFacturaById(id);
        return new ResponseEntity<>(factura, HttpStatus.OK);
    }

}
