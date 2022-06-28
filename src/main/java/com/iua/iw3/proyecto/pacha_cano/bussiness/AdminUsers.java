package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.ChangeRolesRequest;

import java.util.List;
import java.util.Set;

public interface AdminUsers {

    User changeRole(ChangeRolesRequest changeRolesRequest) throws BusinessException, NotFoundException;
    List<Rol> listAllRoles() throws BusinessException;
    User enableDisableUser(Long id) throws BusinessException, NotFoundException;
}
