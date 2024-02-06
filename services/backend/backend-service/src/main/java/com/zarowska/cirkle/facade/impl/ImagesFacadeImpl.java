package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import com.zarowska.cirkle.domain.service.ImagesService;
import com.zarowska.cirkle.facade.ImagesFacade;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.net.URI;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesFacadeImpl implements ImagesFacade {

	@PersistenceContext
	private final EntityManager entityManager;

	private final ImagesService imagesService;

	private final UserEntityMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public ImageDto downloadById(UUID imageId, Integer width, Integer height) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		return imagesService.findById(user, imageId, width, height);
	}

	@Override
	public FileDto save(MultipartFile image) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		FileInfoEntity entity = imagesService.save(user, image);
		return new FileDto().id(entity.getId()).url(URI.create("/images/%s".formatted(entity.getId())))
				.size(entity.getSize()).owner(mapper.toDto(user)).mediaType(entity.getMediaType().toString())
				.uploadedAt(entity.getUploadedAt().atOffset(ZoneOffset.UTC));
	}
}
