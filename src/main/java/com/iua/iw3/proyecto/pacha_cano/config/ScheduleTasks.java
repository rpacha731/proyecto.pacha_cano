package com.iua.iw3.proyecto.pacha_cano.config;

import com.iua.iw3.proyecto.pacha_cano.security.authtoken.IAuthTokenBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@EnableScheduling
@AllArgsConstructor
public class ScheduleTasks {

    private IAuthTokenBusiness authTokenBusiness;

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)
    private void purgeTokens() {
        try {
            authTokenBusiness.purgeTokens();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
