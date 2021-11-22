package com.iua.iw3.proyecto.pacha_cano.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocs () {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo () {
        return new ApiInfoBuilder()
                .title("Sistema de gestión de órdenes de carga de gas líquido - IW3")
                .version("1.0.0")
                .description("API para gestionar órdenes de carga de gas líquido. Esto se" +
                        "realiza creando una orden de carga, adjuntando datos como el preset, tara, etc," +
                        "a la orden de carga. Finalmente se cierra la orden de carga, la cual puede" +
                        "ser consultada en el futuro. Cada orden de carga pasa por 4 estados: E1, E2, " +
                        "E3 y E4.\n" +
                        "Se utilizó una dependencia llamada Lombok que ayudó bastante a la hora de hacer " +
                        "getters y setters, constructores y autowired.\n" +
                        "<strong> IMPLEMENTADO POR PACHA, LEONEL Y CANO, ELIANA </strong>")
                .build();
    }
}
