package com.GimnasioBerserker.Socios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI sociosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Socios API – Gimnasio Berserker")
                        .description("""
                                API REST para la gestión de socios y membresías del Gimnasio Berserker.
                                Permite crear, consultar, actualizar y eliminar socios,
                                así como obtener la rutina asignada a cada uno mediante
                                comunicación con el microservicio de Rutinas.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Gimnasio Berserker")
                                .email("contacto@gimnasioberserker.cl"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));

        // Se elimina .servers(...) para que Swagger detecte automáticamente el host del Gateway
    }
}