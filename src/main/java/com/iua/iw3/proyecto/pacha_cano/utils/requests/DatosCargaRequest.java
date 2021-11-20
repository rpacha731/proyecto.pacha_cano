package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DatosCargaRequest {
    private Long numeroOrden;
    private Integer password;
    private Double masaAcumulada;
    private Double densidad;
    private Double temperatura;
    private Double caudal;
}
