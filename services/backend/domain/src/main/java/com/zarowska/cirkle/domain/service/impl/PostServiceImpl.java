package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.PostEntity;
import com.zarowska.cirkle.domain.repository.PostEntityRepository;
import com.zarowska.cirkle.domain.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostEntityRepository postEntityRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PostEntity save(PostEntity postEntity) {
		return postEntityRepository.save(postEntity);
	}

	@Override
	public List<PostEntity> findAllByAuthorId(UUID userId) {
		return postEntityRepository.findAllByAuthorId(userId);
	}

	@Override
	public Optional<PostEntity> findById(UUID postId) {
		return postEntityRepository.findById(postId);
	}

}
