package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BussinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.FoundException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserNegocio implements IUserNegocio {

    private UserRepository userRepository;

    @Override
    public User load(Long id) throws BussinessException, NotFoundException {
        Optional<User> o;
        try {
            o = userRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BussinessException(e);
        }
        if (o.isEmpty()) throw new NotFoundException("No se encontró el usuario con id = " + id);
        return o.get();
    }

    @Override
    public List<User> listado() throws BussinessException {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BussinessException(e);
        }
    }

    @Override
    public User addUser(User user) throws BussinessException, FoundException {
        try {
            load(user.getId());
            loadByEmail(user.getEmail());
            throw new FoundException("Ya existe un usuario con el mail/id = " + user.getEmail() + " / " + user.getId());
        } catch (NotFoundException n) { }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BussinessException(e);
        }
    }

    @Override
    public User modifyUser(User user) throws BussinessException, NotFoundException {
        load(user.getId());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BussinessException(e);
        }
    }

    @Override
    public User loadByEmail(String email) throws BussinessException, NotFoundException {
        Optional<User> o;
        try {
            o = userRepository.findFirstByEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BussinessException(e);
        }
        if (o.isEmpty()) throw new NotFoundException("No se encontró el usuario con email = " + email);
        return o.get();
    }
}
