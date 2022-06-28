package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.DuplicateException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.WrongDateException;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.List;

public interface OrdenCargaBusiness {

    List<OrdenCarga> listAll() throws BusinessException;
    List<Producto> listAllProductos() throws BusinessException;
    List<Camion> listAllCamiones() throws BusinessException;
    List<Chofer> listAllChoferes() throws BusinessException;
    List<Cliente> listAllClientes() throws BusinessException;
    List<OrdenCarga> listAllByEstado(Integer indiceEstado) throws BusinessException, NotFoundException;
    OrdenCarga load(Long idOrdenCarga) throws BusinessException, NotFoundException;
    OrdenCarga loadByNumeroOrden (Long numOrden) throws BusinessException, DuplicateException;
    OrdenCarga getByNumeroOrden (Long numOrden) throws BusinessException, NotFoundException;
    OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException;
    OrdenCarga adjuntarTara (PesoInicialRequest pesoInicialRequest) throws BusinessException, NotFoundException, WrongDateException;
    String adjuntarDatoCarga (DatosCargaRequest datosCargaRequest) throws BusinessException, NotFoundException;
    OrdenCarga cerrarOrden (Long numeroOrden) throws BusinessException, NotFoundException;
    Conciliacion adjuntarPesoFinal (PesoFinalRequest pesoFinalRequest) throws BusinessException, NotFoundException;
    Conciliacion getConciliacion (Long numeroOrden) throws BusinessException, NotFoundException;
    FileInputStream generateCSVOrdenCarga (Long numeroOrden) throws BusinessException;
    OrdenCarga modify(OrdenCarga ordenCarga) throws BusinessException, NotFoundException;
    void deleteById(Long idOrdenCarga) throws BusinessException, NotFoundException;
    OrdenCarga cambiarFrecuencia(Long numeroOrden, Integer frecuencia) throws BusinessException, NotFoundException;
}
