package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.RolRepository;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserBusiness;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.ChangeRolesRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class AdminUsersImpl implements AdminUsers {

    private final UserBusiness userBusiness;
    private final RolRepository rolRepository;

    @Override
    public User changeRole(ChangeRolesRequest changeRolesRequest) throws BusinessException, NotFoundException {
        User user = this.userBusiness.load(changeRolesRequest.getIdUser());
        user.setRoles(changeRolesRequest.getRoles());
        return this.userBusiness.modifyUser(user);
    }

    @Override
    public List<Rol> listAllRoles() throws BusinessException {
        try {
            return this.rolRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public User enableDisableUser(Long id) throws BusinessException, NotFoundException {
        User user = this.userBusiness.load(id);
        user.setEnabled(!user.isEnabled());
        return this.userBusiness.modifyUser(user);
    }

}
