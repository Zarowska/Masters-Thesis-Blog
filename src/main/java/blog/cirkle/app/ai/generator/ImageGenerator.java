package blog.cirkle.app.ai.generator;

import static blog.cirkle.app.ai.generator.GenerationUtils.extractJson;
import static blog.cirkle.app.ai.generator.GenerationUtils.selectInterest;

import blog.cirkle.app.ai.generator.api.*;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Profile("data-generation")
public class ImageGenerator {

	private static String serverUrl = "http://10.10.10.7:8080/v1/images/generations";

	private static final PromptTemplate promptTemplate = new PromptTemplate("""
			Create a realistic {{photo_type}} of a {{gender}} named {{name}}, who is {{nationality}}.
			They have {{appearance}}. In the {{photo_type}}, they are {{activity}}. The setting is {{setting}}.
			""");

	private static final PromptTemplate settingPromptTemplate = new PromptTemplate(
			"""
					I need to generate setting and activity for {{photo_type}} photo of {{age}} yo {{nationality}} {{gender}}, that have {{appearance}}, doing {{title}} as {{description}}
					return response in JSON format with following fields:
					{
					  "setting": "photo setting",
					  "activity": "activity on photo"
					}

					""");

	private final MistralAiChatModel mistralMiniModel;

	private RestTemplate restTemplate = new RestTemplate();

	public File generateAvatar(PersonDescription author) {

		ImageSettingResponse imageSetting = generateSettings("profile", author);

		ImageGenerationRequest request = ImageGenerationRequest.builder().author(author).photoType("profile photo")
				.activity(imageSetting.getActivity()).setting(imageSetting.getActivity()).build();
		return generateImage(request);
	}

	private ImageSettingResponse generateSettings(String type, PersonDescription author) {
		PersonDescription.PersonInterest interest = selectInterest(author);

		UserMessage message = settingPromptTemplate.apply(Map.of("photo_type", type, "age", author.getAge(), "gender",
				author.getGender(), "nationality", author.getNationality(), "appearance", author.getAppearance(),
				"title", interest.getTitle(), "description", interest.getDescription())).toUserMessage();

		return extractJson(mistralMiniModel.generate(message), ImageSettingResponse.class);
	}

	public File generateImage(ImageGenerationRequest request) {
		String prompt = promptTemplate.apply(Map.of("photo_type", request.getPhotoType(), "gender",
				request.getAuthor().getGender(), "name", request.getAuthor().getFullName(), "nationality",
				request.getAuthor().getNationality(), "appearance", request.getAuthor().getAppearance(), "activity",
				request.getActivity(), "setting", request.getSetting())).text();
		ImgGenResponse response = restTemplate.postForObject(serverUrl, new ImgGenRequest(prompt),
				ImgGenResponse.class);
		Optional<File> result = response.getData().stream().findFirst().map(ImgGenResponse.Generated::getUrl)
				.map(this::downloadAndConvertImage);
		if (result.isEmpty()) {
			throw new RuntimeException("Failed to generate the image.");
		}
		return result.get();
	}

	private File downloadAndConvertImage(String imageUrl) {
		// Step 1: Download the image using RestTemplate
		ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

		if (response.getBody() == null || response.getStatusCode().isError()) {
			throw new RuntimeException("Failed to download the image from the provided URL. " + imageUrl);
		}

		// Convert the byte array to a BufferedImage
		byte[] imageBytes = response.getBody();
		BufferedImage pngImage = null;
		try {
			pngImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (pngImage == null) {
			throw new RuntimeException("Downloaded image is not valid or not in PNG format.");
		}

		// Step 2: Convert the image to JPEG format
		BufferedImage jpegImage = new BufferedImage(pngImage.getWidth(), pngImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		// Draw the PNG image on the new RGB (JPEG compatible) BufferedImage
		jpegImage.createGraphics().drawImage(pngImage, 0, 0, null);

		// Step 3: Save the image to a file with a random UUID name in the temp
		// directory
		String tempDir = System.getProperty("java.io.tmpdir");
		String randomFileName = UUID.randomUUID() + ".jpg";
		File outputFile = new File(tempDir, randomFileName);

		try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
			ImageIO.write(jpegImage, "jpg", fileOutputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed to save the image to a file.", e);
		}

		// Step 4: Return the saved File
		return outputFile;
	}

}
