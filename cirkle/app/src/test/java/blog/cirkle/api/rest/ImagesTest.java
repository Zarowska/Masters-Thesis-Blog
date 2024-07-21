package blog.cirkle.api.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.domain.model.response.FileDto;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

class ImagesTest extends AbstractApiTest {

	public static final String SUNSET_IMAGE = "./src/test/resources/test-data/sunset.jpg";

	@Test
	void shouldUploadAndDownloadImage() {
		asAlice(ctx -> {
			FileDto upload = ctx.files().upload(new File(SUNSET_IMAGE));
			Optional<byte[]> bytes = ctx.files().byId(upload.getId(), null, null);
			assertThat(bytes.isPresent()).isTrue();
			assertThat(bytes.get().length).isEqualTo(upload.getSize());
		});
	}

	@Test
	void shouldResizeDownloadImage() {
		asAlice(ctx -> {
			FileDto upload = ctx.files().upload(new File(SUNSET_IMAGE));
			BufferedImage original = convertToBufferedImage(ctx.files().byId(upload.getId(), null, null).get());
			assertThat(original.getWidth()).isEqualTo(612);
			assertThat(original.getHeight()).isEqualTo(408);
			BufferedImage resize300h = convertToBufferedImage(ctx.files().byId(upload.getId(), 300, null).get());
			assertThat(resize300h.getWidth()).isEqualTo(450);
			assertThat(resize300h.getHeight()).isEqualTo(300);

			BufferedImage resize300w = convertToBufferedImage(ctx.files().byId(upload.getId(), null, 300).get());
			assertThat(resize300w.getWidth()).isEqualTo(300);
			assertThat(resize300w.getHeight()).isEqualTo(200);

			BufferedImage resize200h200w = convertToBufferedImage(ctx.files().byId(upload.getId(), 200, 200).get());
			assertThat(resize200h200w.getWidth()).isEqualTo(200);
			assertThat(resize200h200w.getHeight()).isEqualTo(133);
		});
	}

	public BufferedImage convertToBufferedImage(byte[] imageInByte) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageInByte);
		return ImageIO.read(byteArrayInputStream);
	}
}
