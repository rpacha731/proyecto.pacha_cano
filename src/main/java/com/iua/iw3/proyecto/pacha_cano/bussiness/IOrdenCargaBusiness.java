package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.DuplicateException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.WrongDateException;
import com.iua.iw3.proyecto.pacha_cano.model.Conciliacion;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.DatosCargaRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoFinalRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.PesoInicialRequest;

import java.util.List;

public interface IOrdenCargaBusiness {

    List<OrdenCarga> listAll() throws BusinessException;

    List<OrdenCarga> listAllByEstado(Integer indiceEstado) throws BusinessException;

    OrdenCarga load(Long idOrdenCarga) throws BusinessException, NotFoundException;

    OrdenCarga loadByNumeroOrden (Long numOrden) throws BusinessException, DuplicateException;

    OrdenCarga getByNumeroOrden (Long numOrden) throws BusinessException, NotFoundException;

    OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException;

    OrdenCarga adjuntarTara (PesoInicialRequest pesoInicialRequest) throws BusinessException, WrongDateException;

    String adjuntarDatoCarga (DatosCargaRequest datosCargaRequest) throws BusinessException;

    OrdenCarga cerrarOrden (Long numeroOrden) throws BusinessException, NotFoundException;

    Conciliacion adjuntarPesoFinal (PesoFinalRequest pesoFinalRequest) throws BusinessException;

    Conciliacion generateConciliacion (Long numeroOrden) throws BusinessException;

    String generateCSVOrdenCarga (Long numeroOrden) throws BusinessException;

    OrdenCarga modify(OrdenCarga ordenCarga) throws BusinessException, NotFoundException;

    void deleteById(Long idOrdenCarga) throws BusinessException, NotFoundException;

}
