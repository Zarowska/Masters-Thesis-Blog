package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.domain.model.ImageDto;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesFacade {
	@Transactional(readOnly = true)
	ImageDto downloadById(UUID imageId, Integer width, Integer height);

	@Transactional
	FileDto save(MultipartFile image);
}
