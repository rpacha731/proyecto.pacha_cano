package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.AuthResponse;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.LoginRequest;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.SignupRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse signUp(SignupRequest signupRequest);

    void logout(String tokenEncript);

    AuthResponse authInfo(String tokenEncript) throws BusinessException;

    User getUserActual();
}
