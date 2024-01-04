package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.PostEntity;
import java.util.List;
import java.util.UUID;

public interface PostService {
	PostEntity save(PostEntity postEntity);

	List<PostEntity> findAllByAuthorId(UUID userId);
}
