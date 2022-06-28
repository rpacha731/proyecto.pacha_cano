package com.iua.iw3.proyecto.pacha_cano.controllers;

import com.iua.iw3.proyecto.pacha_cano.bussiness.AdminUsersImpl;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserBusiness;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.ChangeRolesRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.MsgResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
@Api(description = "REST Controller para el inicio de sesión y autenticación")
public class AdminController extends UtilsRest {

    private final UserBusiness userBusiness;
    private final AdminUsersImpl adminUsers;

    @ApiOperation(value = "Lista de usuarios",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de usuarios"),
            @ApiResponse(code = 409, message = "Error del servidor | Error al obtener la lista de usuarios"),
    })
    @GetMapping(value = "admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarUsuarios() {
        try {
            return ResponseEntity.ok(userBusiness.listado());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Cambiar roles de un usuario",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario con roles cambiados"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 409, message = "Error del servidor | Error al cambiar los roles del usuario")
    })
    @PutMapping(value = "admin/users/changeRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeRole(@RequestBody @NotEmpty ChangeRolesRequest changeRolesRequest) {
        try {
            return ResponseEntity.ok(adminUsers.changeRole(changeRolesRequest));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Lista de roles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de roles"),
            @ApiResponse(code = 409, message = "Error del servidor | Error al obtener la lista de roles"),
    })
    @GetMapping(value = "admin/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarRoles() {
        try {
            return ResponseEntity.ok(adminUsers.listAllRoles());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Habilita/Deshabilita un usuario",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario habilitado/deshabilitado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 409, message = "Error del servidor | Error al habilitar/deshabilitar el usuario")
    })
    @PutMapping(value = "admin/users/enable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enableUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(adminUsers.enableDisableUser(id));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
