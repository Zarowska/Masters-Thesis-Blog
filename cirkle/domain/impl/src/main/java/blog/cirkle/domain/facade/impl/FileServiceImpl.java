package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.resource.ImageFile;
import blog.cirkle.domain.exception.BadRequestException;
import blog.cirkle.domain.repository.resource.ImageFileRepository;
import blog.cirkle.domain.service.FileService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final ImageFileRepository imageFileRepository;

	@Override
	public ImageFile create(MultipartFile file) {
		try {
			return imageFileRepository.save(new ImageFile(file));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public Optional<ImageFile> findById(UUID id) {
		return imageFileRepository.findById(id);
	}

}
