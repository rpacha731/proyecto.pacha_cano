package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Request del peso final", description = "Representa los datos que se envían para el peso final")
public class PesoFinalRequest {

    @ApiModelProperty(notes = "Número de la orden a adjuntar el peso final", required = true)
    private Long numeroOrden;

    @ApiModelProperty(notes = "Peso final", required = true)
    private Double pesoFinal;
}
