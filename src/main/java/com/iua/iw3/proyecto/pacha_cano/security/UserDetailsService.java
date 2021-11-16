package com.iua.iw3.proyecto.pacha_cano.security;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.IUserBusiness;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private IUserBusiness userNegocio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userNegocio.loadByEmail(username);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return user;
    }
}
