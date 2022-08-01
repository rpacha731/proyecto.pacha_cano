package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ApiModel(value = "Request de dato de carga", description = "Representa los datos que se envían en tiempo real " +
        "con la masa acumulada, densidad, temperatura y caudal")
public class DatosCargaRequest {

    @ApiModelProperty(notes = "Número de orden a la que se le adjuntara el dato de carga", required = true)
    private Long numeroOrden;

    @ApiModelProperty(notes = "Password, que hace referencia a que la bomba está funcionando", required = true)
    private Integer password;

    @ApiModelProperty(notes = "Masa acumulada de gas liquido", required = true, allowableValues = "range[0, 1)")
    private Double masaAcumulada;

    @ApiModelProperty(notes = "Densidad del gas liquido", required = true, allowableValues = "range[0, 1]")
    private Double densidad;

    @ApiModelProperty(notes = "Temperatura del gas liquido", required = true)
    private Double temperatura;

    @ApiModelProperty(notes = "Caudal del gas liquido", required = true)
    private Double caudal;

}
