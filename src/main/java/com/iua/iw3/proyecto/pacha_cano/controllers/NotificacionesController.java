package com.iua.iw3.proyecto.pacha_cano.controllers;

import com.iua.iw3.proyecto.pacha_cano.bussiness.NotificacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Api(description = "REST Controller para notificaciones")
public class NotificacionesController {

    private final NotificacionService notificacionService;

    @ApiOperation(value = "Obtiene las notificaciones del usuario")
    @ApiResponse(code = 200, message = "Lista de notificaciones del usuario")
    @GetMapping("/notificaciones-user")
    public ResponseEntity<?> notificacionesUsuario() {
        return new ResponseEntity<>(this.notificacionService.notificacionesPorUsuario(), HttpStatus.OK);
    }

    @ApiOperation(value = "Lee una notificación")
    @ApiResponse(code = 200, message = "Notificación leída")
    @PostMapping("/notificaciones-user/leer")
    public ResponseEntity<?> leerNotificacionUsuario(@RequestParam("idNotif") Long idNotificacion) {
        return new ResponseEntity<>(this.notificacionService.leerNotificacion(idNotificacion), HttpStatus.OK);
    }

    @ApiOperation(value = "Borra una notificación")
    @ApiResponse(code = 200, message = "Notificación borrada")
    @PostMapping("/notificaciones-user/borrar")
    public ResponseEntity<?> borrarNotificacionUsuario(@RequestParam("idNotif") Long idNotificacion) {
        this.notificacionService.borrarNotificacion(idNotificacion);
        return new ResponseEntity<>(this.notificacionService.notificacionesPorUsuario(), HttpStatus.OK);
    }
}
