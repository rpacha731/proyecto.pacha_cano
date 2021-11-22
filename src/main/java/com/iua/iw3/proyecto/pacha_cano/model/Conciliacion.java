package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
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
