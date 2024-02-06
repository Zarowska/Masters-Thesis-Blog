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
 * A delegate to be called by the {@link PostReactionsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface PostReactionsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users/{userId}/posts/{postId}/reactions : Add posts&#39;s reaction
     * Add reaction to post
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param createReactionRequest  (optional)
     * @return OK (status code 200)
     * @see PostReactionsApi#addPostReaction
     */
    default ResponseEntity<Reaction> addPostReaction(UUID userId,
        UUID postId,
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
     * DELETE /users/{userId}/posts/{postId}/reactions/{reactionId} : Delete post reaction by id
     * Delete post reaction by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param reactionId  (required)
     * @return No Content (status code 204)
     * @see PostReactionsApi#deletePostReactionById
     */
    default ResponseEntity<Void> deletePostReactionById(UUID userId,
        UUID postId,
        UUID reactionId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId}/reactions/{reactionId} : Get post reaction by id
     * Get post reaction by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param reactionId  (required)
     * @return OK (status code 200)
     * @see PostReactionsApi#getPostReactionById
     */
    default ResponseEntity<Reaction> getPostReactionById(UUID userId,
        UUID postId,
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
     * GET /users/{userId}/posts/{postId}/reactions : Get post&#39;s reaction
     * Get post reactions
     *
     * @param userId  (required)
     * @param postId  (required)
     * @return OK (status code 200)
     * @see PostReactionsApi#getPostReactions
     */
    default ResponseEntity<ReactionList> getPostReactions(UUID userId,
        UUID postId) {
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
