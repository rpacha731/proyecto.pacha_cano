package com.iua.iw3.proyecto.pacha_cano.web;

import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthToken;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.IAuthTokenBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class UtilsRest {

    @Autowired
    private IAuthTokenBusiness authTokenBusiness;

    public UtilsRest () { super(); }

    protected User getUserLogged() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected JSONObject userToJson(User user) {
        Optional<AuthToken> o = authTokenBusiness.getAuthtokenByUser(user);
        AuthToken token;
        if (o.isEmpty())
            token = authTokenBusiness.generateAuthToken(user);
        else
            token = o.get();
//        AuthToken token = authTokenBusiness.generateAuthToken(user);
        String tokenEncrip = token.encodeTokenValue();
        JSONObject j = new JSONObject();
        j.put("username", user.getUsername());
        j.put("idUser", user.getId());
        j.put("email", user.getEmail());
        JSONArray a = new JSONArray();
        for (GrantedAuthority g : user.getAuthorities())
            a.put(g.getAuthority());
        j.put("roles", a);
        j.put("authtoken", tokenEncrip);
        return j;
    }

    /**
     * Método que calcula la fecha de expiración. Por defecto, se añade 1 día a la fecha actual.
     * @return {@link Date} de expiración.
     */
    private Date fechaExpiracion() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 60);
        date = calendar.getTime();
        return date;
    }
}
