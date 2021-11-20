package com.iua.iw3.proyecto.pacha_cano.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iua.iw3.proyecto.pacha_cano.bussiness.IOrdenCargaBusiness;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.OrdenCargaJsonSerializer;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;
import com.iua.iw3.proyecto.pacha_cano.utils.MsgResponse;
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

    @PostMapping(value = "ordenes-carga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearOrdenCarga (@RequestBody OrdenCarga ordenCarga) {
        OrdenCarga nuevaOrden;
        try {
            nuevaOrden = ordenCargaBusiness.create(ordenCarga);
            String orden = JsonUtils
                    .getObjectMapper(OrdenCarga.class, new OrdenCargaJsonSerializer(OrdenCarga.class), null)
                            .writeValueAsString(nuevaOrden);

            if (nuevaOrden == null)
                return new ResponseEntity<>(orden, HttpStatus.OK);
            return new ResponseEntity<>(orden, HttpStatus.OK);
        } catch (BusinessException | FoundException | JsonProcessingException e) {
            return new ResponseEntity<>(new MsgResponse(500, e.getMessage()).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
