package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BussinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;

import java.util.List;

public interface IUserNegocio {

    User load(Long id) throws BussinessException, NotFoundException;

    List<User> listado() throws BussinessException;

    User addUser(User user) throws BussinessException, FoundException;

    User modifyUser(User user) throws BussinessException, NotFoundException;

    User loadByEmail(String email) throws BussinessException, NotFoundException;

}
