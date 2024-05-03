package blog.cirkle.api.rest.files;

import blog.cirkle.domain.facade.ImageFacade;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {

	private final ImageFacade imageFacade;

	@PostMapping
	public ResponseEntity<Void> uploadFile(@RequestParam("image") MultipartFile file) {
		UUID fileId = imageFacade.uploadFile(file);
		return ResponseEntity.created(URI.create("/api/v1/images/%s".formatted(fileId))).build();
	}
}
