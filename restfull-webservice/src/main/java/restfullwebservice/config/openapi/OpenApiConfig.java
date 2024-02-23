package restfullwebservice.config.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = " MyREST API Documentation",
    version = "${api.version}",
    contact = @Contact(
      name = "John Smith", email = "john.smith@gmail.com", url = "https://www.john-smith.io"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    termsOfService = "${tos.uri}",
    description = "${api.description}"
  ),
  servers = @Server(
    url = "${api.server.url}",
    description = "Development"
  )
)
public class OpenApiConfig {
	@Bean
	public OpenAPI customizeOpenAPI() {
	    final String securitySchemeName = "bearerAuth";
	    return new OpenAPI()
	      .addSecurityItem(new SecurityRequirement()
	        .addList(securitySchemeName))
	      .components(new Components()
	        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
	          .name(securitySchemeName)
	          .type(SecurityScheme.Type.HTTP)
	          .scheme("bearer")
	          .bearerFormat("JWT")));
	    }
	}
