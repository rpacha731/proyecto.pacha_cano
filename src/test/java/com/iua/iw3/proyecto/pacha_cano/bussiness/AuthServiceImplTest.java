package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.controllers.AuthController;
import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthToken;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthTokenBusiness;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.AuthTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceImplTest {

    private static AuthToken authToken;
    private static User usr;

    @BeforeClass
    public static void setup() {
        Set<Rol> roles = new HashSet<Rol>();
        roles.add(Rol.builder().nombre("ROLE_ADMIN").descripcion("Admin").build());

        usr = User.builder().nombre("Eliana")
                .apellido("Cano")
                .email("ecano043@alumnos.iua.edu.ar")
                .enabled(true)
                .roles(roles).build();

        Calendar exp = Calendar.getInstance();
        exp.setTime(new Date());
        exp.add(Calendar.HOUR, -24);
        authToken = new AuthToken(usr, exp.getTime());
    }

    @MockBean
    AuthTokenBusiness authTokenMock;
    @MockBean
    AuthTokenRepository authTokenRepositoryMock;

    @Test
    public void testTokenValido() throws BusinessException { //Este test está mal si devuelve que el token es válido

        //Simulamos que se busca el token de este user en la BD, pero devolvemos el mock
        when(authTokenRepositoryMock.findByUsername("ecano043@alumnos.iua.edu.ar")).thenReturn(Optional.of(authToken));

        AuthToken tokenRecibido = authTokenRepositoryMock.findByUsername("ecano043@alumnos.iua.edu.ar").get();
        assertEquals(false, tokenRecibido.valid());
        log.info("------------------ TOKEN VÁLIDO: " + tokenRecibido.valid());
    }
}
