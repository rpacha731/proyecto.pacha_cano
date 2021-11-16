package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;

import java.util.List;

public interface IUserBusiness {

    User load(Long id) throws BusinessException, NotFoundException;

    List<User> listado() throws BusinessException;

    User addUser(User user) throws BusinessException, FoundException;

    User modifyUser(User user) throws BusinessException, NotFoundException;

    User loadByEmail(String email) throws BusinessException, NotFoundException;

}
