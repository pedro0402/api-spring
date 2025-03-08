package com.pedro.jpa.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    String campo;

    public CampoInvalidoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

}
