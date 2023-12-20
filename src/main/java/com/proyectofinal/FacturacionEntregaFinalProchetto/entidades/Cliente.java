package com.proyectofinal.FacturacionEntregaFinalProchetto.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clienteid;

    @Column(name="dni", nullable = false)
    private Integer dni;

    @Column(name="nombre", nullable = false)
    private String nombre;

    private String apellido;

    //Constructor
    public Cliente() {
    }

    //Getters & Setters
    public Integer getClienteid() {
        return clienteid;
    }

    public void setClienteid(Integer clienteid) {
        this.clienteid = clienteid;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //StringBuiilder
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        if (clienteid != null) {
            strBuilder.append("clienteid").append(clienteid).append(", ");
        }
        if (dni != null) {
            strBuilder.append("dni=").append(dni);
        }
        strBuilder.append("]");
        return strBuilder.toString();
    }

}


