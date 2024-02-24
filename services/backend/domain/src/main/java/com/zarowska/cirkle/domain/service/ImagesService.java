package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
	Optional<ImageDto> findById(UUID imageId, Integer width, Integer height);

	FileInfoEntity save(UserEntity user, MultipartFile image);

	Page<FileInfoEntity> listByOwner(UserEntity user, Pageable pageable);

	Optional<FileInfoEntity> getImageInfoById(UUID imageId);

	void deleteById(UserEntity user, UUID imageId);
}
