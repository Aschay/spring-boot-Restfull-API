package restfullwebservice.config.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = " MyREST API Documentation",
                                version = "${api.version}",
                                contact = @Contact(name = "John Smith",
                                                  email = "john.smith@gmail.com",
                                                  url = "https://www.john-smith.io"), 
                                license = @License(name = "Apache 2.0",
                                                   url = "https://www.apache.org/licenses/LICENSE-2.0"),
                                                   termsOfService = "${tos.uri}",
                                                   description = "${api.description}"),
                               servers = @Server(url = "${api.server.url}", 
                               description = "Development")
                  )
public class OpenApiConfig {

	@Value("${keycloak.auth-server-url}")
	String authServerUrl;
	@Value("${keycloak.realm}")
	String realm;

	private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";

	@Bean
	public OpenAPI customizeOpenAPI() {
		return new OpenAPI()
				  .components(new Components().addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
				   .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
	}
	

	 private SecurityScheme createOAuthScheme() {
	        OAuthFlows flows = createOAuthFlows();
	        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
	            .flows(flows);
	    }

	    private OAuthFlows createOAuthFlows() {
	        OAuthFlow flow = createAuthorizationCodeFlow();
	        return new OAuthFlows().implicit(flow);
	    }

	    private OAuthFlow createAuthorizationCodeFlow() {
	        return new OAuthFlow()
	            .authorizationUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
	            .scopes(new Scopes().addString("read_access", "read data")
	                .addString("write_access", "modify data"));
	    }
}
