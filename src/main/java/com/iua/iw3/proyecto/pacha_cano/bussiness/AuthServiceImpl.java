package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.AutenticacionException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.RolRepository;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthToken;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthTokenBusiness;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.AuthResponse;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.LoginRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.SignupRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenBusiness authTokenBusiness;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = this.userRepository.findFirstByEmail(loginRequest.getUserEmail())
                .orElseThrow(() -> new AutenticacionException("El usuario no existe / Bad credentials."));

        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getPassword()));

        AuthToken authToken = this.authTokenBusiness.generateAuthToken(user);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return AuthResponse.builder()
                .email(user.getEmail())
                .tokenEncript(authToken.encodeTokenValue())
                .expiraEn(authToken.getToDate())
                .roles(new ArrayList<>(user.getRoles()))
                .username(user.getUsername())
                .build();
    }

    @Override
    public AuthResponse signUp(SignupRequest signupRequest) {
        Rol user = this.rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new AutenticacionException("ROLE_USER no cargado."));

        if (this.userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent())
            throw new AutenticacionException("El email ya está registrado.");

        if (signupRequest.getPassword().length() < 8)
            throw new AutenticacionException("La contraseña debe tener al menos 8 caracteres.");

        Set<Rol> roles = new HashSet<>();
        roles.add(user);
        User usuario = User.builder()
                .apellido(signupRequest.getApellido())
                .nombre(signupRequest.getNombre())
                .email(signupRequest.getEmail())
                .enabled(true)
                .password(this.passwordEncoder.encode(signupRequest.getPassword()))
                .roles(roles)
                .build();

        AuthToken aux;

        try {
            usuario = this.userRepository.save(usuario);
            aux = this.authTokenBusiness.generateAuthToken(usuario);
        } catch (Exception e) {
            throw new AutenticacionException("Error al generar el token / Guardar el usuario.");
        }

        return AuthResponse.builder()
                .email(usuario.getEmail())
                .tokenEncript(aux.encodeTokenValue())
                .expiraEn(aux.getToDate())
                .roles(new ArrayList<>(usuario.getRoles()))
                .username(usuario.getUsername())
                .build();
    }

    @Override
    public void logout(String tokenEncript) {
        this.authTokenBusiness.delete(tokenEncript);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public AuthResponse authInfo(String tokenEncript) throws BusinessException {

        AuthToken aux = this.authTokenBusiness.getAuthToken(tokenEncript);
        User user = this.userRepository.findFirstByEmail(aux.getUsername())
                .orElseThrow(() -> new AutenticacionException("Usuario no encontrado."));

        if (aux.valid())
            return AuthResponse.builder()
                    .username(aux.getUsername())
                    .tokenEncript(aux.encodeTokenValue())
                    .roles(new ArrayList<>(user.getRoles()))
                    .expiraEn(aux.getToDate())
                    .mensaje("Token Valido")
                    .build();

        return AuthResponse.builder()
                .username(aux.getUsername())
                .tokenEncript(aux.encodeTokenValue())
                .roles(new ArrayList<>(user.getRoles()))
                .expiraEn(aux.getToDate())
                .mensaje("Token Invalido")
                .build();

    }

    @Override
    public User getUserActual() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
