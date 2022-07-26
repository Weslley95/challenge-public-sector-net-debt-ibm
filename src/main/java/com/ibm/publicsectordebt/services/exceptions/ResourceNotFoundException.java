package com.ibm.publicsectordebt.services.exceptions;

/**
 * Exception personalizada da camada service
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Exception para camada service
     * @param message mensagem da exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
