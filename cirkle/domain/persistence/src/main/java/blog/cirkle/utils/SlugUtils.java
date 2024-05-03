package blog.cirkle.utils;

import com.github.slugify.Slugify;
import java.util.Random;
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

}
