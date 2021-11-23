package com.iua.iw3.proyecto.pacha_cano.security;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.NotFoundException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.IUserBusiness;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthToken;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.IAuthTokenBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private IAuthTokenBusiness authTokenBusiness;
    private IUserBusiness userBusiness;

    public AuthTokenFilter (IAuthTokenBusiness authTokenBusiness, IUserBusiness userBusiness) {
        super();
        this.authTokenBusiness = authTokenBusiness;
        this.userBusiness = userBusiness;
    }

    public static String ORIGIN_TOKEN_TOKEN = "token";
    public static String ORIGIN_TOKEN_HEADER = "header";

    public static String AUTH_HEADER = "X-AUTH-TOKEN";
    public static String AUTH_HEADER1 = "XAUTHTOKEN";
    public static String AUTH_PARAMETER = "xauthtoken";
    public static String AUTH_PARAMETER1 = "token";

    public static String AUTH_PARAMETER_AUTHORIZATION = "Authorization";

    private boolean esValido(String valor) {
        return valor != null && valor.trim().length() > 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String parameter = request.getParameter(AUTH_PARAMETER);
        if (!esValido(parameter)) {
            parameter = request.getParameter(AUTH_PARAMETER1);
        }
        String header = request.getHeader(AUTH_HEADER);
        if (!esValido(header)) {
            header = request.getHeader(AUTH_PARAMETER_AUTHORIZATION);
            if(header != null && header.toLowerCase().startsWith("bearer ")) {
                header=header.substring("Bearer ".length());
            }
        }
        if (!esValido(parameter) && !esValido(header)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = "";
        if (esValido(parameter)) {
            token = parameter;
            log.trace("Token recibido por query param=" + token);
        } else {
            token = header;
            log.trace("Token recibido por header=" + token);
        }
        String[] tokens = null;
        AuthToken authToken = null;

        try {
            tokens = AuthToken.decode(token);
            if (tokens.length != 2) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            filterChain.doFilter(request, response);
            return;
        }

        // A partir de aquí, se considera que se envió el token, por
        // ende si no está ok, login inválido

        try {
            authToken = authTokenBusiness.getAuthToken(token);
        } catch (BusinessException e) {
            SecurityContextHolder.clearContext();
            log.error(e.getMessage(), e);
            filterChain.doFilter(request, response);
            return;
        }

        if (!authToken.valid()) {
            SecurityContextHolder.clearContext();
            log.debug("El Token " + token + " ha expirado");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authToken.setLastUsed(new Date());
//            authToken.addRequest();
            authTokenBusiness.saveToken(authToken);
            String username = authToken.getUsername();
            User u ;
            try {
                u = userBusiness.loadByEmail(username);
                log.info("Token para usuario {} ({}) [{}]", u.getUsername(), token, request.getRequestURI());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(u, null,
                        u.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (NotFoundException e) {
                log.debug("No se encontró el usuario {} por token", username);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            filterChain.doFilter(request, response);
        }
    }
}
