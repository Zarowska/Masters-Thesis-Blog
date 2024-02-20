package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoEntityRepository extends JpaRepository<FileInfoEntity, UUID> {
	List<FileInfoEntity> findByOwnerId(UserEntity owner);

	Page<FileInfoEntity> findAllByOwner(UserEntity owner, Pageable pageable);

}