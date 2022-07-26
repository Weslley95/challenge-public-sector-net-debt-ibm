package com.ibm.publicsectordebt.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * Class para exception personalizada
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Instant instant;
    private Integer status;
    private String error;
}
