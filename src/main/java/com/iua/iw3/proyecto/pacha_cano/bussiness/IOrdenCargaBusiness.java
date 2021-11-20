package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.DuplicateException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;

import java.util.List;

public interface IOrdenCargaBusiness {

    List<OrdenCarga> listAll() throws BusinessException;

    OrdenCarga load(Long idOrdenCarga) throws BusinessException, NotFoundException;

    OrdenCarga loadByNumeroOrden (Long numOrden) throws BusinessException, DuplicateException;
    OrdenCarga create(OrdenCarga ordenCarga) throws BusinessException, DuplicateException;

    OrdenCarga modify(OrdenCarga ordenCarga) throws BusinessException, NotFoundException;

    void deleteById(Long idOrdenCarga) throws BusinessException, NotFoundException;

}
