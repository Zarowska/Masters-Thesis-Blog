package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface PostCommentFacade {

	@Transactional
	Comment createPostComment(UUID userId, UUID postId, CreateCommentRequest createCommentRequest);

	@Transactional
	Comment getPostCommentById(UUID userId, UUID postId, UUID commentId);
}
