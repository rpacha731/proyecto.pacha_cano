package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.CamionJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name="camiones")
@JsonSerialize(using = CamionJsonSerializer.class)
@ApiModel(value = "Camión", description = "Clase que describe al camión")
public class Camion implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Patente del camión", example = "ABC123", required = true)
    @Column(nullable = false, unique = true)
    private String patente;

    @ApiModelProperty(notes = "Descripción del camión", example = "Modelo 2017", required = true)
    @Column (length = 100)
    private String descripcion;

    @ApiModelProperty(notes = "Cisternado del camión", required = true)
    @Column (nullable = false)
    private Long cisternado;

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo del camión", example = "CAM_12345", required = true)
    private String codigoExterno;

}
