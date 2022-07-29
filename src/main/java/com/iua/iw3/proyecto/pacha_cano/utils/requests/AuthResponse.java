package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

        private String tokenEncript;

        private String username;

        private String email;

        private List<Rol> roles;

        private Date expiraEn;

        private String mensaje;
}
