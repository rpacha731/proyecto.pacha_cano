package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.ProductoJsonSerializer;
import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="productos")
@JsonSerialize(using = ProductoJsonSerializer.class)
@ApiModel(value = "Producto", description = "Clase que describe al producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Nombre del producto", example = "Gas Liquido", required = true)
    @Column(nullable = false, unique = true)
    private String nombre;

    @ApiModelProperty(notes = "Descripción del producto", example = "Gas GNC Liquido Volátil", required = true)
    @Column(length = 100)
    private String descripcion;

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo del producto", example = "GNC_LV", required = true)
    private String codigoExterno;
}
