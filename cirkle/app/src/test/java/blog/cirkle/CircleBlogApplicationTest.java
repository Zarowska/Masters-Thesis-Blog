package blog.cirkle;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircleBlogApplicationTest {

	@LocalServerPort
	private int port;

	@Test
	void loadContext() throws IOException {
		// ApiClient apiClient = new ApiClient("http://localhost:" + port);
		// apiClient.registration().register("abc@cde.com", "John", "Doe", "12345");
		// apiClient.auth().login("abc@cde.com", "12345");
		// UserDto current = apiClient.user().current();
		// System.out.println(current);
		// System.out.println(apiClient.users().list());
		// System.out.println(apiClient.users().byId(current.getId()));
		// System.out.println(apiClient.users().bySlug(current.getSlug()));
		// assertTrue(uploadAndDownloadFile(apiClient,
		// "test-data/cirkle.jpg").isPresent());
		// assertTrue(uploadAndDownloadFile(apiClient,
		// "test-data/cirkle.gif").isPresent());
		// assertTrue(uploadAndDownloadFile(apiClient,
		// "test-data/cirkle-png8.png").isPresent());
		// assertTrue(uploadAndDownloadFile(apiClient,
		// "test-data/cirkle-png24.png").isPresent());
	}

	// @Test
	// void createPostTest() throws IOException {
	// ApiClient apiClient = new ApiClient("http://localhost:" + port);
	// apiClient.registration().register("abc1@cde.com", "John", "Doe", "12345");
	// UserDto current = apiClient.user().current();
	// PostDto helloWorld = apiClient.posts().create(current.getId(), new
	// CreatePostDto().setText("Hello World"));
	// System.out.println(helloWorld);
	//
	// PaginatedResponse<PostDto> posts =
	// apiClient.posts().findByUserId(current.getId());
	// System.out.println(posts);
	//
	// }
	//
	// private Optional<byte[]> uploadAndDownloadFile(ApiClient apiClient, String
	// imageName) throws IOException {
	// return uploadAndDownloadResizedFile(apiClient, imageName, null, null);
	// }
	//
	// private Optional<byte[]> uploadAndDownloadResizedFile(ApiClient apiClient,
	// String imageName, Integer height,
	// Integer width) throws IOException {
	// URL resource = this.getClass().getClassLoader().getResource(imageName);
	// String uploadUrl = apiClient.files().upload(new File(resource.getFile()));
	// UUID imageId = UUID.fromString(uploadUrl.substring(uploadUrl.lastIndexOf('/')
	// + 1));
	// Optional<byte[]> bytes = apiClient.files().byId(imageId, height, width);
	// return bytes;
	// }

}