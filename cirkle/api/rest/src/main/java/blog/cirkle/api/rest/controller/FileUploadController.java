package blog.cirkle.api.rest.controller;

import blog.cirkle.domain.facade.ImageFacade;
import blog.cirkle.domain.model.response.FileDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class FileUploadController {

	private final ImageFacade imageFacade;

	@PostMapping
	public ResponseEntity<FileDto> uploadFile(@RequestParam("image") MultipartFile file) {
		FileDto fileDto = imageFacade.uploadFile(file);
		URI location = URI.create("/api/v1/images/%s".formatted(fileDto.getId()));
		return ResponseEntity.created(location).body(fileDto.withUrl(location.toString()));
	}
}
