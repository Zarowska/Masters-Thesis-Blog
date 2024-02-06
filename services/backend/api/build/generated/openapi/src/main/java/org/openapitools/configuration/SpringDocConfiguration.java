package org.openapitools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Cirkle")
                                .description("The Cirkle Blog Rest API is a powerful and versatile set of endpoints designed to power a social network platform similar to Facebook. This comprehensive API allows developers to integrate Cirkle Blog's social networking features into their applications, enabling users to connect, share, and interact with one another in a seamless and engaging manner. Whether you're building a web application, mobile app, or any other digital platform, Cirkle Blog's Rest API provides the tools you need to create a thriving online community.")
                                .termsOfService("https://circle.blog/terms-of-service")
                                .contact(
                                        new Contact()
                                                .name("Oksana Zarowska")
                                )
                                .version("1.0")
                )
                .components(
                        new Components()
                                .addSecuritySchemes("bearer-key", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                )
                )
        ;
    }
}