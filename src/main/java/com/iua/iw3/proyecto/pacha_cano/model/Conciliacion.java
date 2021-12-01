package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(notes = "Masa acumulada total al cerrar la carga", example = "30")
    private Double masaAcumuladaTotal;

    @ApiModelProperty(notes = "Temperatura promedio", example = "30")
    private Double temperaturaPromedio;

    @ApiModelProperty(notes = "Densidad promedio", example = "0.11")
    private Double densidadPromedio;

    @ApiModelProperty(notes = "Caudal promedio", example = "11.2")
    private Double caudalPromedio;
}
