package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
            new Info()
                .title("SpringBoot Board REST API")
                .description("스프링 부트 기반의 게시판 REST API")
                .version("v1.0")
                .license(new License().name("Apache 2.0").url("http://myapiserver.com"))
            );
    }
}
