package blog.cirkle.app.utils;

import java.util.UUID;

public class UUIDExtractor {

	public static UUID[] extractUUIDs(String concatenatedUUIDs) {
		String firstUUIDString = concatenatedUUIDs.substring(0, 32);
		String secondUUIDString = concatenatedUUIDs.substring(32);

		UUID firstUUID = UUID.fromString(insertHyphens(firstUUIDString));
		UUID secondUUID = UUID.fromString(insertHyphens(secondUUIDString));

		return new UUID[]{firstUUID, secondUUID};
	}

	private static String insertHyphens(String uuidString) {
		return uuidString.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
				"$1-$2-$3-$4-$5");
	}
}