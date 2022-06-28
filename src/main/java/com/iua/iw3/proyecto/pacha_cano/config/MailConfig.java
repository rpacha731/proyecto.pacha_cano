package com.iua.iw3.proyecto.pacha_cano.config;

import dev.ditsche.mailo.config.MailoConfig;
import dev.ditsche.mailo.config.SmtpConfig;
import dev.ditsche.mailo.provider.MailProvider;
import dev.ditsche.mailo.provider.SmtpMailProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

    private String MAIL_FROM;
    private String HOST;
    private String USERNAME;
    private String PASSWORD;
    private Integer PORT;

    @Bean
    public MailoConfig mailoConfig() {
        MailoConfig config = MailoConfig.get();
        config.setTemplateDirectory("./templates/");
        config.setMjmlAppId("27f66c36-12ab-4b67-8950-6306920369c3");
        config.setMjmlAppSecret("1167ef3c-a114-4ca3-a6b6-aa2342678d8d");
        return config;
    }

    @Bean
    public MailProvider mailProvider() {
        SmtpConfig config = new SmtpConfig();
        config.setHost(HOST);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setPort(PORT);
        return new SmtpMailProvider(config);
    }

    @Bean
    public String mailFrom() {
        return this.MAIL_FROM;
    }
}
