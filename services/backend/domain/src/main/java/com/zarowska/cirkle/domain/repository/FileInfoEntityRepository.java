package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoEntityRepository extends JpaRepository<FileInfoEntity, UUID> {
	List<FileInfoEntity> findByOwnerId(UUID ownerId);
	//
	// Page<FileInfoEntity> getImageInfoList(Integer page, Integer size);
	//
	// FileInfoEntity getImageInfoById(UUID imageId);
}