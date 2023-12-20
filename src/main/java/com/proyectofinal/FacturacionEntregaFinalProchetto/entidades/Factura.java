package com.proyectofinal.FacturacionEntregaFinalProchetto.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="factura")
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facturaid;

    private Integer cantidad;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "clienteid", referencedColumnName = "clienteid")
    private Cliente cliente;

    private BigDecimal total;


    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private Set<Linea> lineas = new HashSet<>();

    //Constructor
    public Factura() {
    }

    //Getters & Setters
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(Set<Linea> lineas) {
        this.lineas = lineas;
    }

    //Patron de gestión de relaciones bidireccionales

    public void addLinea(Linea linea) {
        if (linea != null && !this.lineas.contains(linea)) {
            this.lineas.add(linea);
            linea.setFactura(this);
        }
    }

    public Linea removeLinea(Linea linea) {
        getLineas().remove(linea);
        linea.setFactura(null);
        return linea;
    }

    //StringBuilder
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        if (facturaid != null) {
            strBuilder.append("facturaid").append(facturaid).append(", ");
        }
        if (cantidad != null) {
            strBuilder.append("cantidad=").append(cantidad);
        }
        if (fecha != null) {
            strBuilder.append("fecha=").append(fecha);
        }
        if (total != null) {
            strBuilder.append("cliente=").append(cliente);
        }
        if (lineas != null) {
            strBuilder.append("lineas=").append(lineas);
        }
        strBuilder.append("]");
        return strBuilder.toString();
    }

    //Hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((facturaid == null) ? 0 : facturaid.hashCode());
        return result;
    }


}