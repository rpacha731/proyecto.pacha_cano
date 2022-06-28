package com.iua.iw3.proyecto.pacha_cano.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "endpoints.urls")
@Configuration
@Data
public class EndpointProperties {

    private String URL_BASE;

    @Bean
    public String urlBase() {
        return this.URL_BASE;
    }

}
