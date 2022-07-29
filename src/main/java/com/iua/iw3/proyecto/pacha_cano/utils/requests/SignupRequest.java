package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull(message = "El nombre del usuario es obligatorio.")
    private String nombre;

    @NotNull(message = "El apellido del usuario es obligatorio.")
    private String apellido;

    @NotNull(message = "El email del usuario es obligatorio.")
    private String email;

    @NotNull(message = "La contraseña es obligatoria.")
    private String password;
}
