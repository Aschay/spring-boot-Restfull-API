package restfullwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		OpenAPI myApiDoc = new OpenAPI();
		Info infos = new Info();
		infos.description("This is generic restfull api that implements crud operations");
		infos.title("Api documentation");
		infos.version("1.0");
		infos.termsOfService("urn:tos");
		Contact owner = new Contact();
		owner.setEmail("john.smith@gmail.com");
		owner.setName("John Smith");
		owner.setUrl("https://www.john-smith.io");
		infos.setContact(owner);
		License license = new License();
		license.setName("Apache-2.0");
		license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
		infos.setLicense(license);
		myApiDoc.setInfo(infos);
		myApiDoc.setComponents(new Components());
		return myApiDoc;

	}
}
