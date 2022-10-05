package org.mesutormanli.customerapi.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mesutormanli"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "customer-api",
                "A very simple Spring Boot REST API, performs CRUD operations on a SQLite database, documented with Swagger2.",
                "1.0",
                "",
                new Contact("Mesut ORMANLI", "https://mesutormanli.info",
                        "mesutormanli@gmail.com"),
                "GNU General Public License v3.0",
                "https://www.gnu.org/licenses/gpl-3.0.html", new ArrayList<>()
        );
    }
}
