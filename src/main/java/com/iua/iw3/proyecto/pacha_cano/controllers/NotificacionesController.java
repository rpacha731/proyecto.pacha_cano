package com.iua.iw3.proyecto.pacha_cano.controllers;

import com.iua.iw3.proyecto.pacha_cano.bussiness.NotificacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Api(description = "REST Controller para notificaciones")
public class NotificacionesController {

    private final NotificacionService notificacionService;

    @ApiOperation(value = "Obtiene las notificaciones del usuario", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponse(code = 200, message = "Lista de notificaciones del usuario")
    @GetMapping("/notificaciones-user")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> notificacionesUsuario() {
        return new ResponseEntity<>(this.notificacionService.notificacionesPorUsuario(), HttpStatus.OK);
    }

    @ApiOperation(value = "Lee una notificación", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponse(code = 200, message = "Notificación leída")
    @PostMapping("/notificaciones-user/leer")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> leerNotificacionUsuario(@RequestParam("idNotif") Long idNotificacion) {
        return new ResponseEntity<>(this.notificacionService.leerNotificacion(idNotificacion), HttpStatus.OK);
    }

}
