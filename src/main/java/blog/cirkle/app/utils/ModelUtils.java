package blog.cirkle.app.utils;

import static blog.cirkle.app.utils.SecurityUtils.getCurrentUser;

import blog.cirkle.app.api.rest.model.request.MediaUpdate;
import blog.cirkle.app.exception.ForbiddenException;
import blog.cirkle.app.model.entity.*;
import blog.cirkle.app.service.ImageService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUtils {
	public static void assertAuthor(Authored item, User currentUser) {
		if (!item.getAuthor().getId().equals(currentUser.getId())) {
			throw new ForbiddenException("You not allowed to update other user post");
		}
	}

	public static void assertAuthoredByCurrentUser(Authored item) {
		if (!item.getAuthor().getId().equals(getCurrentUser().getId())) {
			throw new ForbiddenException("You not allowed to update other user post");
		}
	}

	public static void updateMedia(ImageService imageService, Media media, MediaUpdate mediaUpdate) {
		Set<Image> images = imageService.validateImages(mediaUpdate.getImages());
		media.setImages(images);
		media.setTextContent(mediaUpdate.getText());
	}
}
