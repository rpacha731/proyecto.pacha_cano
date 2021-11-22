package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Request de LOGIN", description = "Representa los datos que se envían para el LOGIN")
public class LoginRequest {

    @ApiModelProperty(notes = "Email del usuario que se hará el LOGIN", required = true)
    private String userEmail;

    @ApiModelProperty(notes = "Password del usuario", required = true)
    private String password;
}
