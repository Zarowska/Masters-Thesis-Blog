package blog.cirkle.app.ai.generator;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerationUtils {

	private static final ObjectMapper objectMapper;
	private static final Random random = new Random();

	static {
		objectMapper = new ObjectMapper();
	}

	private static final String[] countries = {"China", "India", "United States", "Indonesia", "Pakistan", "Nigeria",
			"Brazil", "Bangladesh", "Russia", "Mexico", "Japan", "Ethiopia", "Philippines", "Egypt", "Vietnam",
			"Turkey", "Iran", "Germany", "Thailand", "United Kingdom", "France", "Italy", "South Africa", "Tanzania",
			"Myanmar", "South Korea", "Colombia", "Kenya", "Spain", "Argentina", "Algeria", "Sudan", "Ukraine",
			"Uganda", "Iraq", "Poland", "Canada", "Morocco", "Saudi Arabia", "Uzbekistan", "Peru", "Angola", "Malaysia",
			"Ghana", "Mozambique", "Yemen", "Nepal", "Venezuela", "Madagascar", "Cameroon", "Côte d'Ivoire",
			"Australia", "North Korea", "Niger", "Taiwan", "Sri Lanka", "Burkina Faso", "Mali", "Malawi", "Chile",
			"Kazakhstan", "Zambia", "Guatemala", "Ecuador", "Syria", "Netherlands", "Senegal", "Cambodia", "Chad",
			"Somalia", "Zimbabwe", "Guinea", "Rwanda", "Benin", "Burundi", "Tunisia", "Bolivia", "Belgium", "Haiti",
			"Cuba", "South Sudan", "Dominican Republic", "Czech Republic", "Greece", "Jordan", "Portugal", "Azerbaijan",
			"Sweden", "Honduras", "United Arab Emirates", "Hungary", "Tajikistan", "Belarus", "Austria",
			"Papua New Guinea", "Serbia", "Israel", "Switzerland", "Togo", "Sierra Leone", "Hong Kong", "Laos",
			"Paraguay", "Bulgaria", "Libya", "Lebanon"};

	// Common person statures
	private static final String[] BUILDS = {"Slim", "Skinny", "Normal", "Athletic", "Muscular", "Stocky"};

	// Common person heights
	private static final String[] HEIGHTS = {"Short", "Medium", "Tall", "Very Tall"};

	private static final String[] freeEmailDomains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "aol.com",
			"icloud.com", "mail.com", "zoho.com", "yandex.com", "protonmail.com", "gmx.com", "live.com"};

	public static String getRandomCountry() {
		return countries[random.nextInt(countries.length)];
	}

	public static String getRandomGender() {
		return Math.random() * 100 < 51 ? "female" : "male";
	}

	public static String getRandomBuild() {
		return BUILDS[random.nextInt(BUILDS.length)];
	}

	public static String getRandomHeight() {
		return HEIGHTS[random.nextInt(HEIGHTS.length)];
	}

	public static PersonDescription.PersonInterest selectInterest(PersonDescription author) {
		return author.getInterests().stream().findFirst().orElseGet(() -> new PersonDescription.PersonInterest("Posing",
				"Standing in front of office corridor, with open space and modern design office on the back."));
	}

	public static <T> T extractJson(Response<AiMessage> response, Class<T> clazz) {
		return extractJson(response.content().text(), clazz);
	}

	public static <T> T extractJson(String str, Class<T> clazz) {
		try {
			String json = extractJson(str);
			return objectMapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String extractJson(String str) {
		int start = str.indexOf('{');
		int end = str.lastIndexOf('}');
		if (start < 0 || end < 0 || start >= end) {
			throw new RuntimeException("Invalid JSON string: " + str);
		}
		return str.substring(start, end + 1);
	}

	public static String randomEmail(String oldEmail) {
		String id = oldEmail.substring(0, oldEmail.indexOf('@'));
		return id + "@" + freeEmailDomains[random.nextInt(freeEmailDomains.length)];
	}

	public static boolean randomDecision(int probability) {
		if (probability>=100) {
			return true;
		} else if (probability<=0) {
			return false;
		} else {
			return random.nextInt(100) < probability;
		}
	}

}
