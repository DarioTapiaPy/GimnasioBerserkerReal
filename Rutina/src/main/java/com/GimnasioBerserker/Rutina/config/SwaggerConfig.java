package com.GimnasioBerserker.Rutina.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI rutinaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rutinas API – Gimnasio Berserker")
                        .description("""
                                API REST para la gestión de rutinas de entrenamiento del Gimnasio Berserker.
                                Permite crear, consultar, actualizar y eliminar rutinas de ejercicio,
                                incluyendo la lista de ejercicios asociados a cada rutina.
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
