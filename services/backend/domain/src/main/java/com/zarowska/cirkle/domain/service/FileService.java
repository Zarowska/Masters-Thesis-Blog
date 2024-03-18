package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	Optional<FileInfoEntity> findById(UUID id);

	List<FileInfoEntity> findByOwner(UserEntity owner);

	void deleteById(UUID id);

	FileInfoEntity upload(UserEntity principal, MultipartFile file);

	Page<FileInfoEntity> findAllByOwner(UserEntity owner, Pageable pageable);
}
