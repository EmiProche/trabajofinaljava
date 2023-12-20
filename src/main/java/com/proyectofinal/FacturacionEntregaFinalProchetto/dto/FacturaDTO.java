package com.proyectofinal.FacturacionEntregaFinalProchetto.dto;

import com.proyectofinal.FacturacionEntregaFinalProchetto.entidades.Cliente;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class FacturaDTO {

    private Integer facturaid;
    private Integer cantidad;
    private Date fecha;
    private BigDecimal total;
    private Cliente cliente;
    private Set<LineaDTO> lineas;

    //Getters & setters
    public Integer getFacturaid() {
        return facturaid;
    }

    public void setFacturaid(Integer facturaid) {
        this.facturaid = facturaid;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<LineaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(Set<LineaDTO> lineas) {
        this.lineas = lineas;
    }

}
