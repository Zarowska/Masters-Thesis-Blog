package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.api.model.FilePage;
import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import com.zarowska.cirkle.domain.service.ImagesService;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.ImagesFacade;
import com.zarowska.cirkle.facade.mapper.FileInfoEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesFacadeImpl implements ImagesFacade {

	@PersistenceContext
	private final EntityManager entityManager;

	private final ImagesService imagesService;

	private final FileInfoEntityMapper imageMapper;

	@Override
	@Transactional(readOnly = true)
	public ImageDto downloadById(UUID imageId, Integer width, Integer height) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		return imagesService.findById(user, imageId, width, height)
				.orElseThrow(() -> new ResourceNotFoundException("image", Map.of("id", imageId)));
	}

	@Override
	public FileDto save(MultipartFile image) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		FileInfoEntity entity = imagesService.save(user, image);
		return imageMapper.toDto(entity);
	}

	@Override
	public FileDto getImageInfoById(UUID imageId) {
		return imagesService.getImageInfoById(imageId).map(imageMapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException("image", Map.of("id", imageId)));
	}

	@Override
	public FilePage getImageInfoList(Integer page, Integer size) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		return imageMapper.toDto(imagesService.listByOwner(user, PageRequest.of(page, size)));
	}

	@Override
	public void deleteById(UUID imageId) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		imagesService.deleteById(user, imageId);
	}

}
