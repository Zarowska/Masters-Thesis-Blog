package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.FileContentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileContentEntityRepository extends JpaRepository<FileContentEntity, UUID> {
}