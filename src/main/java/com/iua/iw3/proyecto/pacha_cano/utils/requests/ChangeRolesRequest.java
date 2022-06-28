package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ApiModel(value = "Request de cambio de rol", description = "Seleccionado un usuario, se pasan los roles que se le quiere asignar")
public class ChangeRolesRequest {

    @ApiModelProperty(notes = "Id del usuario", required = true)
    private Long idUser;

    @ApiModelProperty(notes = "Roles que se le quiere asignar al usuario", required = true)
    private Set<Rol> roles;

}
