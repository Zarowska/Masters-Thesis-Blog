package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostService {
	PostEntity save(PostEntity postEntity);

	List<PostEntity> findAllByAuthorId(UUID userId);
	Optional<PostEntity> findById(UUID postId);

	Page<PostEntity> findByUserId(UUID id, PageRequest pageRequest);
}
