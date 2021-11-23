package com.iua.iw3.proyecto.pacha_cano.security.authtoken;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuthTokenBusiness implements IAuthTokenBusiness {

    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;

    @Override
    public AuthToken generateAuthToken(User usuario) {
        AuthToken aux = new AuthToken(usuario, this.fechaExpiracion());
        this.authTokenRepository.save(aux);
        return aux;
    }

    @Override
    public AuthToken generateAuthToken(String userEmail) {
        AuthToken aux = new AuthToken(userRepository.findFirstByEmail(userEmail).get(), this.fechaExpiracion());
        this.authTokenRepository.save(aux);
        return aux;
    }

    @Override
    public AuthToken getAuthToken(String tokenEncript) throws BusinessException {
        return this.authTokenRepository.findById(AuthToken.decode(tokenEncript)[0])
                .orElseThrow(() -> new BusinessException("Token inválido / No encontrado"));
    }

    @Override
    public void purgeTokens() { this.authTokenRepository.purgeToDate(new Date()); }

    @Override
    public void delete(String tokenEncript) { this.authTokenRepository.deleteById(AuthToken.decode(tokenEncript)[0]); }

    @Override
    public void delete(AuthToken token) { this.authTokenRepository.deleteById(token.getSeries()); }

    @Override
    @Transactional
    public void saveToken(AuthToken token) { this.authTokenRepository.save(token); }

    @Override
    public boolean isValidDate(AuthToken token) { return token.valid(); }

    @Override
    public void extendDateToken(AuthToken token) {
        token.setToDate(this.fechaExpiracion());
        this.authTokenRepository.save(token);
    }

    @Override
    public void extendDateToken(String tokenEncript) {
        AuthToken aux = this.authTokenRepository.findById(AuthToken.decode(tokenEncript)[0]).get();
        aux.setToDate(this.fechaExpiracion());
        this.authTokenRepository.save(aux);
    }

    /**
     * Método que calcula la fecha de expiración. Por defecto, se añade 1 día a la fecha actual.
     * @return {@link Date} de expiración.
     */
    private Date fechaExpiracion() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24);
        return calendar.getTime();
    }
}
