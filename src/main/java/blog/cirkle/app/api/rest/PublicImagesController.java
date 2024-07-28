package blog.cirkle.app.api.rest;

import blog.cirkle.app.facade.ImageFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/public/api/images")
@RequiredArgsConstructor
public class PublicImagesController {

	private final ImageFacade imageFacade;

	@Operation(summary = "Get Image by ID", description = "Get specific image by its ID from database", operationId = "getImageByID", tags = {
			"images"})
	@GetMapping(path = "/{imageId}")
	ResponseEntity<Resource> getImage(@PathVariable UUID imageId) {
		return imageFacade.getResponseEntityWithContentById(imageId);
	}
}
