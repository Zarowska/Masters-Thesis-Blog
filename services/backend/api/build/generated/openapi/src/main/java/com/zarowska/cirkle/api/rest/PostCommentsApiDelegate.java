package com.zarowska.cirkle.api.rest;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CommentPage;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import java.util.UUID;
import com.zarowska.cirkle.api.model.UpdateCommentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link PostCommentsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface PostCommentsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users/{userId}/posts/{postId}/comments : Create a post comment
     * Add comment to the post
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param createCommentRequest  (optional)
     * @return OK (status code 200)
     * @see PostCommentsApi#addPostComment
     */
    default ResponseEntity<Comment> addPostComment(UUID userId,
        UUID postId,
        CreateCommentRequest createCommentRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"images\" : [ null, null ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"postId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /users/{userId}/posts/{postId}/comments/{commentId} : Delete post&#39;s comment
     * Delete post comment by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @return No Content (status code 204)
     * @see PostCommentsApi#deletePostCommentById
     */
    default ResponseEntity<Void> deletePostCommentById(UUID userId,
        UUID postId,
        UUID commentId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId}/comments/{commentId} : Get post&#39;s comment
     * Get post comment by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @return OK (status code 200)
     * @see PostCommentsApi#getPostCommentById
     */
    default ResponseEntity<Comment> getPostCommentById(UUID userId,
        UUID postId,
        UUID commentId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"images\" : [ null, null ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"postId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId}/comments : Get post&#39;s comment
     * List post comments
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param page  (optional)
     * @param size  (optional)
     * @return OK (status code 200)
     * @see PostCommentsApi#listPostCommentsById
     */
    default ResponseEntity<CommentPage> listPostCommentsById(UUID userId,
        UUID postId,
        Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 5, \"last\" : true, \"size\" : 1, \"numberOfElements\" : 5, \"totalPages\" : 0, \"sort\" : { \"unsorted\" : true, \"sorted\" : true, \"empty\" : true }, \"first\" : true, \"content\" : [ { \"images\" : [ null, null ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"postId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }, { \"images\" : [ null, null ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"postId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" } ], \"totalElements\" : 6, \"empty\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /users/{userId}/posts/{postId}/comments/{commentId} : Update post&#39;s comment
     * Update post comment by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @param updateCommentRequest  (optional)
     * @return OK (status code 200)
     * @see PostCommentsApi#updatePostCommentById
     */
    default ResponseEntity<Comment> updatePostCommentById(UUID userId,
        UUID postId,
        UUID commentId,
        UpdateCommentRequest updateCommentRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"images\" : [ null, null ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"postId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
