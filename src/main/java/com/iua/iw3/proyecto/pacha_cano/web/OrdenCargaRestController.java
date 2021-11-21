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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
public class OrdenCargaRestController {

    private IOrdenCargaBusiness ordenCargaBusiness;

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

    @GetMapping(value = "ordenes-carga/E{i}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listadoE4 (@PathVariable("i") int i) {
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

    @PostMapping(value = "ordenes-carga/crear-CSV/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearCSVOrden (@PathVariable("numOrden") long numeroOrden) {
        try {
            return new ResponseEntity<>(ordenCargaBusiness.generateCSVOrdenCarga(numeroOrden), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "ordenes-carga/cerrar/{numOrden}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cerrarOrden (@PathVariable("numOrden") long numeroOrden) {
        try {
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                    .writeValueAsString(ordenCargaBusiness.cerrarOrden(numeroOrden));
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MsgResponse(404, e.getMessage()).toString(), HttpStatus.NOT_FOUND);
        }
    }

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
}
