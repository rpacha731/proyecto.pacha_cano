package com.iua.iw3.proyecto.pacha_cano.controllers;

import com.iua.iw3.proyecto.pacha_cano.bussiness.AuthService;
import com.iua.iw3.proyecto.pacha_cano.exceptions.AutenticacionException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.AuthResponse;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.LoginRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.SignupRequest;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
@Api(description = "REST Controller para el inicio de sesión y autenticación")
public class AuthController{

    private final AuthService authService;

    @ApiOperation(value = "Registro en el servidor",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registro correcto"),
            @ApiResponse(code = 400, message = "BAD_SIGNUP_REQUEST"),
            @ApiResponse(code = 500, message = "Error del servidor | Error al comprobar credenciales | Credenciales incorrectas")
    })
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registro(@RequestBody SignupRequest signupRequest) {
        Map<String, Object> response = new HashMap<>();
        AuthResponse authResponse;
        try {
            authResponse = this.authService.signUp(signupRequest);
        } catch (AutenticacionException e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al registrar usuario");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Login al servidor",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login correcto"),
            @ApiResponse(code = 400, message = "BAD_LOGIN_REQUEST"),
            @ApiResponse(code = 500, message = "Error del servidor | Error al comprobar credenciales | Credenciales incorrectas")
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        AuthResponse authResponse;
        try {
            authResponse = this.authService.login(loginRequest);
        } catch (AutenticacionException e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al registrar usuario");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Devuelve el usuario actualmente logueado",
            produces = MediaType.APPLICATION_JSON_VALUE, authorizations = {@Authorization(value = "Bearer")})
    @ApiResponse(code = 200, message = "Descripción del usuario correcta")
    @GetMapping(value = "/auth-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authInfo(@RequestParam String tokenEncript) {
        AuthResponse authResponse;
        try {
            authResponse = this.authService.authInfo(tokenEncript);
        } catch (AutenticacionException | BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Cierra la sesión actual",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Sesión cerrada")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestParam("token") String tokenEncript) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.authService.logout(tokenEncript);
        } catch (AutenticacionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Sesión cerrada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
