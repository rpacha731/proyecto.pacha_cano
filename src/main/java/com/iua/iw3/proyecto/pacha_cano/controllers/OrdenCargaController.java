package com.iua.iw3.proyecto.pacha_cano.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iua.iw3.proyecto.pacha_cano.bussiness.OrdenCargaBusinessImpl;
import com.iua.iw3.proyecto.pacha_cano.exceptions.*;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.ConciliacionJsonSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.OrdenCargaJsonSerializer;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.MsgResponse;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
@Api(value = "ordenes-carga", description = "REST Controller de las ordenes de carga de gas liquido")
public class OrdenCargaController {

    private final OrdenCargaBusinessImpl ordenCargaBusiness;

    @ApiOperation(value = "Listado completo de las ordenes de carga en todos sus estados", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de las ordenes"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de las ordenes | Error del servidor")
    })
    @GetMapping(value = "ordenes-carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listado() {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAll());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }


    @ApiOperation(value = "Listado completo de los productos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de los productos"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de los productos | Error del servidor")
    })
    @GetMapping(value = "productos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listadoProductos() {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAllProductos());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Listado completo de los camiones", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de los camiones"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de los camiones | Error del servidor")
    })
    @GetMapping(value = "camiones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listadoCamiones() {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAllCamiones());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Listado completo de los clientes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de los clientes"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de los clientes | Error del servidor")
    })
    @GetMapping(value = "clientes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listadoClientes() {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAllClientes());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Listado completo de los choferes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de los choferes"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de los choferes | Error del servidor")
    })
    @GetMapping(value = "choferes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listadoChoferes() {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAllChoferes());
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Buscar orden de carga por id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga encontrada"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Error al obtener la orden de carga | Error del servidor")
    })
    @GetMapping(value = "ordenes-carga/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.load(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Buscar orden de carga por número de orden", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga encontrada"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Error al obtener la orden de carga | Error del servidor")
    })
    @GetMapping(value = "ordenes-carga/numero-orden/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadByNumOrden(@PathVariable("numOrden") Long numOrden) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.getByNumeroOrden(numOrden));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Listar ordenes de carga según el estado", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado de ordenes de carga"),
            @ApiResponse(code = 404, message = "No se encontraron órdenes de carga con estado Ei"),
            @ApiResponse(code = 409, message = "Error al obtener el listado de las ordenes de carga según estado | Error del servidor")
    })
    @GetMapping(value = "ordenes-carga/E{i}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listadoPorEstado(@PathVariable("i") Integer i) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.listAllByEstado(i));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Crear orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Orden de carga creada"),
            @ApiResponse(code = 409, message = "Ya existe una orden de carga con el mismo número de orden | Error del servidor")
    })
    @PostMapping(value = "ordenes-carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearOrdenCarga(@RequestBody OrdenCarga ordenCarga) {
        try {
            return new ResponseEntity<>(this.ordenCargaBusiness.create(ordenCarga), HttpStatus.CREATED);
        } catch (DuplicateException | BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Adjuntar tara a una orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tara adjuntada a la orden de carga"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Error al adjuntar la tara a la orden de carga (fecha) | Error del servidor")
    })
    @PutMapping(value = "ordenes-carga/tara", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> adjuntarTara(@RequestBody PesoInicialRequest pesoInicialRequest) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.adjuntarTara(pesoInicialRequest));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (WrongDateException | BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Adjuntar dato a una orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dato adjuntado a la orden de carga"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Orden cerrada, no se pueden adjuntar más datos | Error del servidor")
    })
    @PutMapping(value = "ordenes-carga/carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> adjuntarDatoCarga(@RequestParam("numeroOrden") Long numeroOrden,
                                               @RequestParam("password") Integer password,
                                               @RequestParam("masaAcumulada") Double masaAcumulada,
                                               @RequestParam("densidad") Double densidad,
                                               @RequestParam("temperatura") Double temperatura,
                                               @RequestParam("caudal") Double caudal) {
        try {
            String estado = this.ordenCargaBusiness.adjuntarDatoCarga(DatosCargaRequest.builder()
                    .numeroOrden(numeroOrden)
                    .password(password)
                    .masaAcumulada(masaAcumulada)
                    .densidad(densidad)
                    .temperatura(temperatura)
                    .caudal(caudal)
                    .build());
            // TODO revisar estado de la orden de carga
            if (estado.equals("OK")) return ResponseEntity.ok(estado);
            if (estado.equals("ORDEN_CERRADA")) return new ResponseEntity<>(estado, HttpStatus.CONFLICT);
            return new ResponseEntity<>(estado, HttpStatus.CONFLICT);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Cerrar una orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga cerrada"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Orden de carga cerrada, no se pueden adjuntar más datos | Error del servidor")
    })
    @PostMapping(value = "ordenes-carga/cerrar/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cerrarOrden(@PathVariable("numOrden") Long numeroOrden) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.cerrarOrden(numeroOrden));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Adjuntar peso final a la orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Peso final adjuntado a la orden de carga"),
            @ApiResponse(code = 409, message = "Error al adjuntar el peso final a la orden de carga | Error del servidor")
    })
    @PutMapping(value = "ordenes-carga/peso-final", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> adjuntarPesoFinal(@RequestBody PesoFinalRequest pesoFinalRequest) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.adjuntarPesoFinal(pesoFinalRequest));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Buscar conciliación de orden de carga por número de orden", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conciliación de orden de carga encontrada"),
            @ApiResponse(code = 409, message = "Conciliación de orden de carga no encontrada | Error del servidor")
    })
    @GetMapping(value = "ordenes-carga/conciliacion/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadConciliacion(@PathVariable("numOrden") Long numOrden) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.getConciliacion(numOrden));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Cambiar frecuencia de carga de una orden de carga", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Frecuencia de carga cambiada"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada"),
            @ApiResponse(code = 409, message = "Error al cambiar la frecuencia de carga | Error del servidor")
    })
    @PutMapping(value = "ordenes-carga/cambiar-frecuencia/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cambiarFrecuencia(@PathVariable("numOrden") Long numeroOrden,
                                               @RequestParam("frecuencia") Integer frecuencia) {
        try {
            return ResponseEntity.ok(this.ordenCargaBusiness.cambiarFrecuencia(numeroOrden, frecuencia));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BusinessException  e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Crear un archivo CSV para generar datos de carga (TEST)", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CSV creado"),
            @ApiResponse(code = 409, message = "Error al crear el CSV | Error del servidor")
    })
    @ApiIgnore
    @GetMapping(value = "test/crear-csv/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearCSVOrden(@PathVariable("numOrden") Long numeroOrden) {
        try {
            return ResponseEntity.ok()
                    .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header("Content-Disposition", "attachment; filename=\"" + "orden_" + numeroOrden + ".csv\"")
                    .body(new InputStreamResource(this.ordenCargaBusiness.generateCSVOrdenCarga(numeroOrden)));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
