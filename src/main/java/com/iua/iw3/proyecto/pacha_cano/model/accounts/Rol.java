package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.RolJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = RolJsonSerializer.class)
@ApiModel(value = "Rol", description = "Clase que describe al rol")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1139806825119468503L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "Nombre del rol", example = "admin", required = true)
    @Column(unique = true, nullable = false)
    private String nombre;

    @ApiModelProperty(notes = "Descripción del rol", example = "Rol con privilegios", required = true)
    private String descripcion;
}
