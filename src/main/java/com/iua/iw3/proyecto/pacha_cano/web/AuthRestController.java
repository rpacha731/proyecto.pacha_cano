package com.iua.iw3.proyecto.pacha_cano.web;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.IUserBusiness;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import com.iua.iw3.proyecto.pacha_cano.utils.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.URL_BASE)
@Slf4j
@AllArgsConstructor
public class AuthRestController extends UtilsRest {

    private IUserBusiness userBusiness;
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<String> registro(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userBusiness.loadByEmail(loginRequest.getUserEmail());
            String msj = user.checkAccount(passwordEncoder, loginRequest.getPassword());
            if (msj != null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ResponseEntity<>(userToJson(getUserLogged()).toString(), HttpStatus.OK);
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("BAD_LOGIN_REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/auth-info")
    public ResponseEntity<String> authInfo() {
        return new ResponseEntity<>(userToJson(getUserLogged()).toString(), HttpStatus.OK);
    }

}
