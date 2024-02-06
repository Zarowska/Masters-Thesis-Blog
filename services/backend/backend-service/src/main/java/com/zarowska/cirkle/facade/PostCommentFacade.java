package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import java.util.UUID;

public interface PostCommentFacade {

	Comment createPostComment(UUID userId, UUID postId, CreateCommentRequest createCommentRequest);
}
