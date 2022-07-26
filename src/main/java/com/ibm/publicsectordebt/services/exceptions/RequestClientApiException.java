package com.ibm.publicsectordebt.services.exceptions;

/**
 * Exception para Feign client na camada de service
 */
public class RequestClientApiException extends RuntimeException {

    // Serial UID
    private static final long serialVersionUID = 1L;

    /**
     * Construtor
     * @param message
     */
    public RequestClientApiException(String message) {
        super(message);
    }
}
