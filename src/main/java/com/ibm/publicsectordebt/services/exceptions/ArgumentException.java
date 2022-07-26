package com.ibm.publicsectordebt.services.exceptions;

/**
 * Exception para argumento ilegal
 */
public class ArgumentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Exception para camada service
     * @param message mensagem da exception
     */
    public ArgumentException(String message) {
        super(message);
    }
}
