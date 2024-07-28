package blog.cirkle.app.service.impl;

import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.model.entity.Participant;
import blog.cirkle.app.repository.ImageRepository;
import blog.cirkle.app.service.ImageService;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageRepository imageRepository;

	private final Tika tika = new Tika();

	@Override
	public Image uploadImage(Participant owner, Resource resource) {
		try {
			byte[] content = resource.getContentAsByteArray();
			String mimeType = tika.detect(content);
			return uploadImage(owner, mimeType, content);
		} catch (IOException e) {
			throw new BadRequestException("Unable to read image content", e);
		}
	}

	@Override
	public Image uploadImage(Participant owner, MultipartFile file) {
		try {
			byte[] content = file.getBytes();
			String mimeType = tika.detect(content);
			return uploadImage(owner, mimeType, content);
		} catch (IOException e) {
			throw new BadRequestException("Unable to read image content", e);
		}
	}

	private Image uploadImage(Participant owner, String mimeType, byte[] content) {
		return imageRepository.save(new Image(owner, mimeType, content));
	}

	@Override
	public Image findById(UUID imageId) {
		return imageRepository.findById(imageId).orElseThrow(() -> NotFoundException.image(imageId));
	}

	@Override
	public Page<Image> findAll(Pageable pageable) {
		return imageRepository.findAll(pageable);
	}

	@Override
	public Set<Image> validateImages(Collection<UUID> imageIds) {
		return imageIds.stream().map(id -> {
			Optional<Image> image = imageRepository.findById(id);
			if (image.isEmpty()) {
				throw NotFoundException.image(id);
			}
			return image.get();
		}).collect(Collectors.toSet());
	}
}
