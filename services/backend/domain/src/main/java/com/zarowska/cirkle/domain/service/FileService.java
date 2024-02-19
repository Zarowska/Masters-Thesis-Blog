package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	Optional<FileInfoEntity> findById(UUID id);

	List<FileInfoEntity> findByOwner(UUID ownerId);

	void deleteById(UUID id);

	FileInfoEntity upload(UserEntity principal, MultipartFile file);
	//
	// Page<FileInfoEntity> getImageInfoList(Integer page, Integer size);
	//
	// FileInfoEntity getImageInfoById(UUID imageId);
}
