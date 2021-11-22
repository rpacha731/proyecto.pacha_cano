package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Request del peso inicial", description = "Representa los datos que se envían para el peso inicial o tara")
public class PesoInicialRequest {

    @ApiModelProperty(notes = "Número de la orden a adjuntar el peso inicial", required = true)
    private Long numeroOrden;

    @ApiModelProperty(notes = "Peso inicial o tara", required = true)
    private Double pesoInicial;
}
