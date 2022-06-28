package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.UserJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = UserJsonSerializer.class)
@ApiModel(value = "Usuario", description = "Clase que describe al usuario")
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID= -1920138525852395737L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Nombre del usuario", example = "Alex", required = true)
    @Column(nullable = false)
    private String nombre;

    @ApiModelProperty(notes = "Apellido del usuario", example = "Cano", required = true)
    private String apellido;

    @ApiModelProperty(notes = "Dirección de mail del usuario", example = "alex@gmail.com", required = true)
    @Column(nullable = false, unique = true)
    private String email;

    @ApiModelProperty(notes = "Contraseña del usuario", required = true)
    @Column(nullable = false)
    private String password;

    @ApiModelProperty(notes = "Estado de habilitación de cuenta - 1 (habilitado) - 0 (deshabilitado)", example = "1", required = true)
    @Column(columnDefinition = "TINYINT DEFAULT 1")
    private boolean enabled;

    @ApiModelProperty(notes = "Roles del usuario", required = true)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="rol_id", referencedColumnName = "id")}
    )
    private Set<Rol> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getNombre()))
                .collect(Collectors.toList());
    }

    public String checkAccount(PasswordEncoder passwordEncoder, String password) {
        if (!isEnabled()) return "ACCOUNT_NOT_ENABLED";
        if (!passwordEncoder.matches(password, getPassword())) return "BAD_PASSWORD";
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

}
