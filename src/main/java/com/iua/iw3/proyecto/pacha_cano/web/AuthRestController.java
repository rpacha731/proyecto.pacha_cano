package com.iua.iw3.proyecto.pacha_cano.web;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.IUserBusiness;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
@Api(description = "REST Controller para el inicio de sesión y autenticación")
public class AuthRestController extends UtilsRest {

    private IUserBusiness userBusiness;
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Registro en el servidor",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registro correcto"),
            @ApiResponse(code = 400, message = "BAD_SIGNUP_REQUEST"),
            @ApiResponse(code = 500, message = "Error del servidor | Error al comprobar credenciales | Credenciales incorrectas")
    })
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registro(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userBusiness.loadByEmail(loginRequest.getUserEmail());
            String msj = user.checkAccount(passwordEncoder, loginRequest.getPassword());
            if (msj != null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ResponseEntity<>(userToJson(getUserLogged()).toString(), HttpStatus.OK);
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("BAD_LOGIN_REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Login al servidor",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login correcto"),
            @ApiResponse(code = 400, message = "BAD_LOGIN_REQUEST"),
            @ApiResponse(code = 500, message = "Error del servidor | Error al comprobar credenciales | Credenciales incorrectas")
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userBusiness.loadByEmail(loginRequest.getUserEmail());
            String msj = user.checkAccount(passwordEncoder, loginRequest.getPassword());
            if (msj != null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ResponseEntity<>(userToJson(getUserLogged()).toString(), HttpStatus.OK);
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("BAD_LOGIN_REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Devuelve el usuario actualmente logueado",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Descripción del usuario correcta")
    @GetMapping(value = "/auth-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authInfo() {
        return new ResponseEntity<>(userToJson(getUserLogged()).toString(), HttpStatus.OK);
    }

}
