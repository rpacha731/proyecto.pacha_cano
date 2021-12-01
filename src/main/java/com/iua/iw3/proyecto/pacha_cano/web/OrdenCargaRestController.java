package com.iua.iw3.proyecto.pacha_cano.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iua.iw3.proyecto.pacha_cano.bussiness.IOrdenCargaBusiness;
import com.iua.iw3.proyecto.pacha_cano.exceptions.*;
import com.iua.iw3.proyecto.pacha_cano.model.Conciliacion;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.ConciliacionJsonSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.OrdenCargaJsonSerializer;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.MsgResponse;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
@Api(value = "ordenes-carga", description = "REST Controller de las ordenes de carga de gas liquido")
public class OrdenCargaRestController {

    private IOrdenCargaBusiness ordenCargaBusiness;

    @ApiOperation(value = "Listado completo de las ordenes de carga en todos sus estados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado completo de las ordenes"),
            @ApiResponse(code = 500, message = "Error del servidor | Error de serialización del listado")
        }
    )
    @GetMapping(value = "ordenes-carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listado () {
        try {
            String listado = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.listAll());
            return new ResponseEntity<>(listado, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Buscar orden de carga por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga encontrada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada")
    }
    )
    @GetMapping(value = "ordenes-carga/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> load (@PathVariable("id") long id) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.load(id));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Buscar orden de carga por número de orden")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga encontrada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada")
    }
    )
    @GetMapping(value = "ordenes-carga/numero-orden/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loadByNumOrden (@PathVariable("numOrden") long numOrden) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.getByNumeroOrden(numOrden));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Listar órdenes de carga según el estado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listado de órdenes de carga"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 404, message = "No se encontraron órdenes de carga con estado Ei")
    }
    )
    @GetMapping(value = "ordenes-carga/E{i}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listadoPorEstado (@PathVariable("i") int i) {
        try {
            String listado = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.listAllByEstado(i));
            return new ResponseEntity<>(listado, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Crear orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Orden de carga creada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 409, message = "Ya existe una orden de carga con el mismo número de orden")
    })
    //@Secured("ROLE_ADMIN")
    @PostMapping(value = "ordenes-carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearOrdenCarga (@RequestBody OrdenCarga ordenCarga) {
        try {
            OrdenCarga nuevaOrden = ordenCargaBusiness.create(ordenCarga);
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                            .writeValueAsString(nuevaOrden);
            return new ResponseEntity<>(orden, HttpStatus.CREATED);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()).toString(), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Adjuntar tara a una orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tara adjuntada a la orden de carga"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 409, message = "Todavía no es la fecha de carga")
    }
    )
    @PutMapping(value = "ordenes-carga/tara", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> adjuntarTara (@RequestBody PesoInicialRequest pesoInicialRequest) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.adjuntarTara(pesoInicialRequest));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (WrongDateException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()).toString(), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Adjuntar dato a una orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dato adjuntado a la orden de carga"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 409, message = "Orden cerrada, no se pueden adjuntar más datos")
    }
    )
    @PutMapping(value = "ordenes-carga/carga")      // VER QUE SE ASEMEJA MEJOR, SI PUT O POST
    public ResponseEntity<String> adjuntarDatoCarga (@RequestParam("numeroOrden") long n, @RequestParam("password") int p, @RequestParam("masaAcumulada") double m,
                                                     @RequestParam("densidad") double d, @RequestParam("temperatura") double t, @RequestParam("caudal") double c) {
        try {
            DatosCargaRequest datosCargaRequest = DatosCargaRequest.builder()
                    .numeroOrden(n).password(p).masaAcumulada(m).densidad(d).temperatura(t).caudal(c).build();
            String estado = ordenCargaBusiness.adjuntarDatoCarga(datosCargaRequest);
            log.warn("Estado" + estado);
            if (estado.equals("OK")) return new ResponseEntity<>(estado, HttpStatus.OK);
            if (estado.equals("ORDEN_CERRADA")) return new ResponseEntity<>(estado, HttpStatus.CONFLICT);
            return new ResponseEntity<>(estado, HttpStatus.CONFLICT);

        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Crear un archivo CSV para generar datos de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CSV creado"),
            @ApiResponse(code = 500, message = "Error del servidor"),
    }
    )
    @ApiIgnore
    @PostMapping(value = "/test/crear-CSV/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearCSVOrden (@PathVariable("numOrden") long numeroOrden) {
        try {
            return new ResponseEntity<>(ordenCargaBusiness.generateCSVOrdenCarga(numeroOrden), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Cerrar una orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden de carga cerrada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada")
    }
    )
    @PostMapping(value = "ordenes-carga/cerrar/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cerrarOrden (@PathVariable("numOrden") long numeroOrden) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.cerrarOrden(numeroOrden));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(409, e.getMessage()).toString(), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Adjuntar peso final a la orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Peso final adjuntado a la orden de carga"),
            @ApiResponse(code = 500, message = "Error del servidor"),
    }
    )
    @PutMapping(value = "ordenes-carga/peso-final", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> adjuntarPesoFinal (@RequestBody PesoFinalRequest pesoFinalRequest) {
        try {
            String conciliacion = JsonUtils
                    .getObjectMapper(Conciliacion.class, new ConciliacionJsonSerializer(Conciliacion.class), null)
                    .writeValueAsString(ordenCargaBusiness.adjuntarPesoFinal(pesoFinalRequest));
            return new ResponseEntity<>(conciliacion, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Cargar conciliación de orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conciliación cargada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
    }
    )
    @GetMapping(value = "ordenes-carga/conciliacion/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loadConciliacion (@PathVariable("numOrden") long numOrden) {
        try {
            String conciliacion = JsonUtils
                    .getObjectMapper(Conciliacion.class, new ConciliacionJsonSerializer(Conciliacion.class), null)
                    .writeValueAsString(ordenCargaBusiness.generateConciliacion(numOrden));
            return new ResponseEntity<>(conciliacion, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Cambiar frecuencia de carga de una orden de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Frecuencia de carga cambiada"),
            @ApiResponse(code = 500, message = "Error del servidor"),
            @ApiResponse(code = 404, message = "Orden de carga no encontrada")
    }
    )
    @PutMapping(value = "ordenes-carga/cambiar-frecuencia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cambiarFrecuencia (@RequestParam("numOrden") long numeroOrden, @RequestParam("frecuencia") Integer frecuencia) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.cambiarFrecuencia(numeroOrden, frecuencia));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        }
    }

}
