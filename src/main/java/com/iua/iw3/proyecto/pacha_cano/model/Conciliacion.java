package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Conciliacion implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    private Long numeroOrden;

    private Double pesoInicial;

    private Double pesoFinal;

    private Double netoBalanza;

    private Double difBalanzaYCaudal;

    private DatosCarga promedioDatosCarga;
}
