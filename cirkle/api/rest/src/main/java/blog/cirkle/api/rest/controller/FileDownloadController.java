package blog.cirkle.api.rest.controller;

import blog.cirkle.domain.facade.ImageFacade;
import blog.cirkle.domain.model.FileWrapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images/{id}")
@RequiredArgsConstructor
public class FileDownloadController {

	private final ImageFacade imageFacade;

	@GetMapping
	public ResponseEntity<Resource> download(@PathVariable UUID id, @RequestParam(required = false) Integer height,
			@RequestParam(required = false) Integer width) {
		FileWrapper image = imageFacade.download(id, width, height);

		return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, image.getMediaType().toString())
				.header(HttpHeaders.CONTENT_DISPOSITION, "filename=" + image.getFileName()).body(image.getResource());
	}
}
