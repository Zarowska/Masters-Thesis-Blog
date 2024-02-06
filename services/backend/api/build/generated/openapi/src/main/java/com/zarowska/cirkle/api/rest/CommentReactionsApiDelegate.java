package com.zarowska.cirkle.api.rest;

import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import java.util.UUID;
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
 * A delegate to be called by the {@link CommentReactionsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface CommentReactionsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users/{userId}/posts/{postId}/comments/{commentId}/reactions : Add reaction to comment
     * Add reaction to the comment
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @param createReactionRequest  (optional)
     * @return OK (status code 200)
     * @see CommentReactionsApi#addCommentReaction
     */
    default ResponseEntity<Reaction> addCommentReaction(UUID userId,
        UUID postId,
        UUID commentId,
        CreateReactionRequest createReactionRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"reactionType\" : 0, \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /users/{userId}/posts/{postId}/comments/{commentId}/reactions/{reactionId} : Delete comment&#39;s reaction
     * Delete comment reaction by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @param reactionId  (required)
     * @return No Content (status code 204)
     * @see CommentReactionsApi#deleteCommentReactionById
     */
    default ResponseEntity<Void> deleteCommentReactionById(UUID userId,
        UUID postId,
        UUID commentId,
        UUID reactionId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId}/comments/{commentId}/reactions/{reactionId} : Get comment&#39;s reaction by id
     * Get comment reaction by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @param reactionId  (required)
     * @return OK (status code 200)
     * @see CommentReactionsApi#getCommentReactionById
     */
    default ResponseEntity<Reaction> getCommentReactionById(UUID userId,
        UUID postId,
        UUID commentId,
        UUID reactionId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"reactionType\" : 0, \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId}/comments/{commentId}/reactions : Get comment&#39;s reactions
     * List comment reactions
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param commentId  (required)
     * @return OK (status code 200)
     * @see CommentReactionsApi#listCommentReactionsById
     */
    default ResponseEntity<ReactionList> listCommentReactionsById(UUID userId,
        UUID postId,
        UUID commentId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"items\" : [ { \"reactionType\" : 0, \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"reactionType\" : 0, \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
