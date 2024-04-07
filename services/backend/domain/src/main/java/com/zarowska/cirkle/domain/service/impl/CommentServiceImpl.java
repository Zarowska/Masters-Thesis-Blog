package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.CommentEntity;
import com.zarowska.cirkle.domain.repository.CommentEntityRepository;
import com.zarowska.cirkle.domain.service.CommentService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentEntityRepository commentEntityRepository;

	@Override
	public CommentEntity save(CommentEntity commentEntity) {
		return commentEntityRepository.save(commentEntity);
	}

	@Override
	public Optional<CommentEntity> findById(UUID commentId) {
		return commentEntityRepository.findById(commentId);
	}
}
