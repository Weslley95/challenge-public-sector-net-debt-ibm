package com.ibm.publicsectordebt.controllers.exceptions;

import com.ibm.publicsectordebt.services.exceptions.ArgumentException;
import com.ibm.publicsectordebt.services.exceptions.RequestClientApiException;
import com.ibm.publicsectordebt.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * Class para tratar as exceptions
 * Interceptar exceptio @ControllerAdvice
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Interceptar qualquer exception 'ResourceNotFoundException'
     * @param e Excecao personalizada
     * @return standardError
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest h) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        var erro = new StandardError(
                Instant.now(),
                httpStatus.value(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(erro);
    }

    /**
     * Interceptar expetion para Feign Client (API externa)
     * @param e Excecao personalizada
     * @return standardError
     */
    @ExceptionHandler(RequestClientApiException.class)
    public ResponseEntity<StandardError> requestFeignClientApi(RequestClientApiException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var erro = new StandardError(
                Instant.now(),
                httpStatus.value(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(erro);
    }

    /**
     * Interceptar expetion para MethodArgumentTypeMismatchException
     * @param e Excecao personalizada
     * @return standardError
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        var erro = new StandardError(
                Instant.now(),
                httpStatus.value(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(erro);
    }

    /**
     * Interceptar expetion para HttpMessageNotReadableException
     * @param e Excecao personalizada
     * @return standardError
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> httpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var erro = new StandardError(
                Instant.now(),
                httpStatus.value(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(erro);
    }

    /**
     * Interceptar expetion para IllegalArgumentException
     * @param e Excecao personalizada
     * @return standardError
     */
    @ExceptionHandler(ArgumentException.class)
    public ResponseEntity<StandardError> argumentException(ArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var erro = new StandardError(
                Instant.now(),
                httpStatus.value(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(erro);
    }
}
