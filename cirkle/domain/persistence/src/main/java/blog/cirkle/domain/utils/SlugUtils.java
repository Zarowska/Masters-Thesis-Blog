package blog.cirkle.domain.utils;

import com.github.slugify.Slugify;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlugUtils {
	private static final Random random = new Random();

	private static final Slugify slg = Slugify.builder().transliterator(true).lowerCase(true).build();

	public static String slugify(String input) {
		return slg.slugify(input);
	}

	public static String slugifyWithSalt(String input) {
		String safeInput = input == null || input.isEmpty() ? "" : input.toLowerCase();
		if (safeInput.length() > 200) {
			safeInput = safeInput.substring(0, 200);
		}
		return slugify(safeInput + " " + random.nextInt(1, 999999));
	}

	public static String slugify(String input, Predicate<String> isUnique) {
		if (input == null) {
			input = "undefined";
		}
		String result = slugify(input);
		while (!isUnique.test(result)) {
			result = slugify(input) + "-" + ThreadLocalRandom.current().nextInt(1000000);
		}
		return result;
	}
}
