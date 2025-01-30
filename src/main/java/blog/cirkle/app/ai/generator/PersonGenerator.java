package blog.cirkle.app.ai.generator;

import static blog.cirkle.app.ai.generator.GenerationUtils.*;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.ai.generator.api.PersonGenerationRequest;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("data-generation")
public class PersonGenerator {

	private final ChatLanguageModel mistralMiniModel;
	private final ChatLanguageModel mistralSmallModel;

	private final PromptTemplate personRequestPromptTemplate = PromptTemplate.from("""
			  Generate fictional {{gender}} character that was born in {{origin}} and now live in {{current}} of {{age}}
			  you should generate name, nationality
			  name and nationality of character should correspond to it's born location
			  return info in JSON format with following fields:
			  {
			     "fullName": "Character Name",
			     "age": 42,
			     "gender": male,
			     "nationality": "Polish",
			     "origin": "City/Country",
			     "liveCity": "London",
			     "liveCountry": "UK",
			  }
			""");

	private final PromptTemplate promptTemplate = PromptTemplate
			.from("""
					I need you to create a fiction character of {{full_name}}, {{age}} yo, {{gender}}, {{build}} build and {{height}} height,
					born in {{country}}, lives now in {{liveCountry}}.

					You need to create solid character that will be used for fictional facebook data generation like posts and comments.

					short appearance should include hair color, stature and height, along with specific features of character.
					example: tall, normal male with long wavy brown hair, warm brown eyes, and a cheerful smile

					person should have 3 to 5 interests

					return results in JSON format with following fields:
					{
					   "fullName": "Aditya Pratama",
					   "age": 28,
					   "gender": "male",
					   "nationality": "Indonesian",
					   "origin": "Jakarta/Indonesia",
					   "liveCity": "London",
					   "liveCountry": "UK",
					   "email": "adityapratama456@example.com",
					   "bio": "Aditya Pratama is a software developer with a passion for technology and travel. He enjoys exploring new cities and trying out local cuisines.",
					   "appearance": "Medium build, short black hair, dark brown eyes, and a friendly smile",
					   "interests": [
					     {
					       "title": "Coding",
					       "description": "Developing software applications and learning new programming languages"
					     },
					     {
					       "title": "Traveling",
					       "description": "Exploring new countries and cultures"
					     },
					     {
					       "title": "Cooking",
					       "description": "Experimenting with Indonesian and international recipes"
					     }
					   ]
					 }
					""");

	public PersonDescription generatePerson() {
		return generatePerson(generatePersonRequest());
	}

	public PersonDescription generatePerson(PersonGenerationRequest request) {
		UserMessage message = promptTemplate.apply(Map.of("full_name", request.getFullName(), "age", request.getAge(),
				"gender", request.getGender(), "country", request.getOrigin(), "liveCity", request.getLiveCity(),
				"liveCountry", request.getLiveCountry(), "build", getRandomBuild(), "height", getRandomHeight()))
				.toUserMessage();

		Response<AiMessage> response = mistralSmallModel.generate(message);

		return extractJson(response, PersonDescription.class);
	}

	public PersonGenerationRequest generatePersonRequest() {
		Prompt prompt = personRequestPromptTemplate.apply(Map.of("origin", getRandomCountry(), "current",
				getRandomCountry(), "age", 15 + Math.random() * 60, "gender", getRandomGender()));
		return extractJson(mistralMiniModel.generate(prompt.toUserMessage()), PersonGenerationRequest.class);
	}
}
