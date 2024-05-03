package blog.cirkle.domain.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {

	public static String uuidsToBase64(UUID uuid1, UUID uuid2) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[32]);
		bb.putLong(uuid1.getMostSignificantBits());
		bb.putLong(uuid1.getLeastSignificantBits());
		bb.putLong(uuid2.getMostSignificantBits());
		bb.putLong(uuid2.getLeastSignificantBits());
		return Base64.getEncoder().encodeToString(bb.array());
	}

	public static UUID[] base64ToUUIDs(String base64) {
		byte[] bytes = Base64.getDecoder().decode(base64);
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		UUID uuid1 = new UUID(bb.getLong(), bb.getLong());
		UUID uuid2 = new UUID(bb.getLong(), bb.getLong());
		return new UUID[]{uuid1, uuid2};
	}
}
