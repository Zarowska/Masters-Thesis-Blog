package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.image.Image;
import blog.cirkle.app.api.graphql.model.image.ImagePage;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller("GraphQLImageController")
public class ImageController {

	@QueryMapping
	public Image getImage(@Argument String imageId) {
		// Implement logic to get an image by ID
		return null; // Replace with actual implementation
	}

	@QueryMapping
	public ImagePage listUserImages(@Argument Integer page, @Argument Integer size, @Argument List<String> sort) {
		// Implement logic to list user's images
		return null; // Replace with actual implementation
	}

	@MutationMapping
	public Image uploadImage(@Argument MultipartFile image) {
		// Implement logic to upload an image
		return null; // Replace with actual implementation
	}
}
