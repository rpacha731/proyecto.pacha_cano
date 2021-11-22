package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="choferes")
@ApiModel(value = "Chofer", description = "Clase que describe al chofer")
public class Chofer implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Nombre del chofer", example = "Alex", required = true)
    @Column(nullable = false)
    private String nombre;

    @ApiModelProperty(notes = "Apellido del chofer", example = "Cano", required = true)
    @Column(nullable = false)
    private String apellido;

    @ApiModelProperty(notes = "DNI del chofer", example = "12345678", required = true)
    @Column(unique = true)
    private Long dni;

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo del chofer", example = "CHF123", required = true)
    private String codigoExterno;
}
