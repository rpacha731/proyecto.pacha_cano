package com.iua.iw3.proyecto.pacha_cano.controller;

import com.iua.iw3.proyecto.pacha_cano.business.OrdenCargaBusinessImpl;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.persistence.OrdenCargaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import org.junit.BeforeClass;

import static org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdenCargaControllerTest {

    private static OrdenCarga orden;

    @Autowired
    private OrdenCargaBusinessImpl ordenCargaBusiness;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setup() {
        orden = OrdenCarga.builder().numeroOrden(111L)
                .camion(new Camion(123L,"ABC111", "desc", 123L, "ABC"))
                .chofer(new Chofer(123L, "Luisa", "Alvarez del Castillo", 42102043L, "ABS"))
                .estado(Estados.E1)
                .cliente(new Cliente())
                .codigoExterno("ABC")
                .fechaHoraTurno(new Date())
                .preset(123L)
                .fechaHoraRecepcion(new Date())
                .build();
    }

    @MockBean
    OrdenCargaBusinessImpl ordenCargaBusinessMock;

    @MockBean
    OrdenCargaRepository ordenCargaRepositoryMock;

    //probar que una orden en E1 no pueda ser cerrada
    @Ignore
    @Test(expected = com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException.class)
    public void testCerrarOrden() throws BusinessException, NotFoundException {

        when(ordenCargaRepositoryMock.findByNumeroOrden(111L)).thenReturn(Optional.of(orden));
        //when(ordenCargaBusinessMock.cerrarOrden(111L)).thenReturn(orden);

        ordenCargaBusiness.cerrarOrden(orden.getId());

        expectedEx.expect(com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException.class);

    }
}



