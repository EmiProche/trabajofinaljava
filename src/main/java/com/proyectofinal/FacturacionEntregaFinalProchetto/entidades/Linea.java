package com.proyectofinal.FacturacionEntregaFinalProchetto.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="linea")
@NamedQuery(name="Linea.findAll", query="SELECT l FROM Linea l")
public class Linea implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer lineaid;

    private Integer cantidad;

    @Column(name="descripcion", nullable = true)
    private String descripcion;

    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name="facturaid")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name="productoid")
    private Producto producto;

    //Constructor
    public Linea() {
    }


    //Getters & setters
    public Integer getLineaid() {
        return lineaid;
    }

    public void setLineaid(Integer lineaid) {
        this.lineaid = lineaid;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    //StringBuilder
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        if (lineaid != null) {
            strBuilder.append("lineaid").append(lineaid).append(", ");
        }
        if (cantidad != null) {
            strBuilder.append("cantidad=").append(cantidad);
        }
        if (descripcion != null) {
            strBuilder.append("descripcion=").append(descripcion);
        }
        if (precio != null) {
            strBuilder.append("precio=").append(precio);
        }
        strBuilder.append("]");
        return strBuilder.toString();
    }

    //Hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lineaid == null) ? 0 : lineaid.hashCode());
        return result;
    }

}
