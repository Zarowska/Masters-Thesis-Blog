package blog.cirkle.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Cirkle API").description("Cirkle Blog Api.").version("1.0.0")
						.contact(new Contact().name("Oksana Zarowska").email("oksana.zarowska@cirkle.blog")
								.url("https://cirkle.blog"))
						.license(new License().name("Apache 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0.html"))
						.termsOfService("https://cirkle.blog/terms"))
				.servers(List.of(new Server().url("https://cirkle.blog").description("Production server"),
						new Server().url("https://stage.cirkle.blog").description("Pre-Production server"),
						new Server().url("https://qa.cirkle.blog").description("QA server"),
						new Server().url("https://dev.cirkle.blog").description("Dev server"),
						new Server().url("http://localhost:8080").description("Local server")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
				.tags(List.of(new Tag().name("auth").description("Authentication and registration"),
						new Tag().name("user").description("User operations"),
						new Tag().name("users").description("Users operations"),
						new Tag().name("groups").description("Groups operations"),
						new Tag().name("posts").description("Post operations"),
						new Tag().name("messages").description("Messages operations"),
						new Tag().name("images").description("Image operations")));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
	}

}
