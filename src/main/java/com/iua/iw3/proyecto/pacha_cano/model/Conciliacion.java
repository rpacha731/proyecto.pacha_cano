package com.iua.iw3.proyecto.pacha_cano.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(value = "Conciliación", description = "Clase que describe a la conciliación")
public class Conciliacion implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @ApiModelProperty(notes = "Número de orden", example = "198887", required = true)
    private Long numeroOrden;

    @ApiModelProperty(notes = "Peso inicial de la orden", example = "123.22", required = true)
    private Double pesoInicial;

    @ApiModelProperty(notes = "Peso final de la orden", example = "123000", required = true)
    private Double pesoFinal;

    @ApiModelProperty(notes = "Resta entre el peso final de la orden y la tara", example = "122876,78", required = true)
    private Double netoBalanza;

    @ApiModelProperty(notes = "Resta entre netoBalanza y última masa acumulada", example = "123000", required = true)
    private Double difBalanzaYCaudal;

    @ApiModelProperty(notes = "Promedio de temperatura, promedio de densidad y promedio de caudal", required = true)
    private DatosCarga promedioDatosCarga;
}
