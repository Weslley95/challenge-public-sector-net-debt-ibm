package com.ibm.publicsectordebt.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Desafio Public Sector Net Debt IBM")
                .description("Desenvolvido por - Weslley")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Weslley", "", "weslley.bezerra@ibm.com"))
                .build();
    }

    private List<ResponseMessage> responseMessageForGET()
    {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("500 message")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(404)
                    .message("Not Found")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(400)
                    .message("Bad Request")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(204)
                    .message("No Content")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(201)
                    .message("Created")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(200)
                    .message("OK")
                    .build());
        }};
    }
}
