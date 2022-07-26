package com.ibm.publicsectordebt.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidade Banco Central Data
 */
@Entity
@Table(name = "bcdata")
@NoArgsConstructor
@Getter @Setter
public class BcData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID do registro")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Data da dívida líquida do setor público")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date data;

    @ApiModelProperty(value = "Valor da dívida líquida do setor público")
    @Column(name = "valor", scale = 2, precision = 3)
    private Double valor;

    /**
     * Construtor com argumentos
     */
    public BcData(BcData bcData) {
        this.data = bcData.getData();
        this.valor = bcData.getValor();
    }
}
