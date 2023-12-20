package com.proyectofinal.FacturacionEntregaFinalProchetto.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="producto")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productoid;

    private Integer codigo;

    private String descripcion;

    private Integer cantidad;

    private BigDecimal precio;


    //Constructor
    public Producto() {
    }

    //Getters & setters
    public Integer getProductoId() {
        return productoid;
    }

    public void setProductoId(Integer productoId) {
        this.productoid = productoId;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    //StringBuilder
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        if (productoid != null) {
            strBuilder.append("productoid").append(productoid).append(", ");
        }
        if (codigo != null) {
            strBuilder.append("codigo=").append(codigo);
        }
        if (cantidad != null) {
            strBuilder.append("cantidad=").append(cantidad);
        }
        strBuilder.append("]");
        return strBuilder.toString();
    }


}
