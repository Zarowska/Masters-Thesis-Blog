package blog.cirkle.app.ai.generator;

import static blog.cirkle.app.ai.generator.GenerationUtils.extractJson;
import static blog.cirkle.app.ai.generator.GenerationUtils.selectInterest;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.ai.generator.api.PostDescription;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.output.Response;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("data-generation")
public class PostGenerator {

	private final MistralAiChatModel mistralLargeModel;

	private static final PromptTemplate promptTemplate = new PromptTemplate(
			"""
					As {{name}}, {{age}} yo {{nationality}} {{gender}}, that is {{appearance}}, I want you to generate a post for me.
					             post topic should be about {{topic}}:{{description}}, you must select post topic, and write 3-5 sentences about it, no need to use hashtags in post.
					             also you need to generate {{image_count}} image descriptions for the post.
					             image description consist of random image with type and activity description and setting
					             example "selfie", "In front of a beautiful landscape in Budapest, with historic buildings in the background.", "Taking a selfie with a few Hungarian students after a successful Muay Thai workshop, everyone looking proud and accomplished."
					 give response in JSON format as follows:
					 {
					   "post": "post text",
					   "images": [
					     {
					       "type": "image type",
					       "setting": "setting",
					       "activity": "activity"
					     }
					   ]
					 }
					""");

	public PostDescription generatePost(PersonDescription author) {
		PersonDescription.PersonInterest interest = selectInterest(author);
		UserMessage message = promptTemplate.apply(Map.of("name", author.getFullName(), "age", author.getAge(),
				"nationality", author.getNationality(), "gender", author.getGender(), "appearance",
				author.getAppearance(), "topic", interest.getTitle(), "description", interest.getDescription(),
				"image_count", 1 + Math.random() * 3)).toUserMessage();
		Response<AiMessage> response = mistralLargeModel.generate(message);
		return extractJson(response, PostDescription.class);
	}
}
