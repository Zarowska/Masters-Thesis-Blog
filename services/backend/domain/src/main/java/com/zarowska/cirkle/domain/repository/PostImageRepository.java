package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.PostImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

	long deleteByImage_Id(UUID id);
}