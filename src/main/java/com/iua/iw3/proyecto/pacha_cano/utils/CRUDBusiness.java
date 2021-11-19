package com.iua.iw3.proyecto.pacha_cano.utils;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CRUDBusiness<T extends Serializable, ID> {

    List<T> listAll() throws BusinessException;

    T load(ID id) throws BusinessException, NotFoundException;

    T create(T object) throws BusinessException, FoundException;

    T modify(T object) throws BusinessException, NotFoundException;

    void deleteById(ID id) throws BusinessException, NotFoundException;

}
