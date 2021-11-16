package com.iua.iw3.proyecto.pacha_cano.security.authtoken;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;

public interface IAuthTokenBusiness {

    AuthToken generateAuthToken(User usuario);

    AuthToken generateAuthToken(String userEmail);

    AuthToken getAuthToken(String tokenEncript) throws BusinessException;

    void purgeTokens();

    void delete(String tokenEncript);

    void delete(AuthToken token);

    void saveToken(AuthToken token);

    boolean isValidDate(AuthToken tokenEncript);

    void extendDateToken(AuthToken token);

    void extendDateToken(String tokenEncript);

}
