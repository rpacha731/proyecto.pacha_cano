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
@Table(name="productos")
@ApiModel(value = "Producto", description = "Clase que describe al producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Nombre del producto", example = "Infinity", required = true)
    @Column(nullable = false, unique = true)
    private String nombre;

    @ApiModelProperty(notes = "Descripción del producto", example = "Eficiencia máxima", required = true)
    @Column(length = 100)
    private String descripcion;

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo del producto", example = "PRD123", required = true)
    private String codigoExterno;
}
