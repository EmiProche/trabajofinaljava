package com.proyectofinal.FacturacionEntregaFinalProchetto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantidadProductosInsuficienteException extends RuntimeException{

    public CantidadProductosInsuficienteException(String message) {
        super(message);
    }

}
