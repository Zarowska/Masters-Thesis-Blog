package blog.cirkle.app.ai.config;

import dev.langchain4j.model.mistralai.MistralAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("data-generation")
public class ModelsConfig {

	private String mistralAiKey = System.getenv("MISTRAL_AI_KEY");

	@Bean
	MistralAiChatModel mistralMiniModel() {
		return MistralAiChatModel.builder().apiKey(mistralAiKey).modelName("ministral-3b-latest").build();
	}

	@Bean
	MistralAiChatModel mistralSmallModel() {
		return MistralAiChatModel.builder().apiKey(mistralAiKey).modelName("mistral-small-latest").build();
	}

	@Bean
	MistralAiChatModel mistralLargeModel() {
		return MistralAiChatModel.builder().apiKey(mistralAiKey).modelName("mistral-large-latest").build();
	}
}
