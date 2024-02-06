package com.zarowska.cirkle.api.rest;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import java.util.UUID;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
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
 * A delegate to be called by the {@link PostsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface PostsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users/{userId}/posts : Create a post
     * Create a new post
     *
     * @param userId  (required)
     * @param createPostRequest  (optional)
     * @return OK (status code 200)
     * @see PostsApi#createPost
     */
    default ResponseEntity<Post> createPost(UUID userId,
        CreatePostRequest createPostRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"images\" : [ null, null ], \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /users/{userId}/posts/{postId} : Delete post by id
     * Delete user&#39;s post by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @return No Content (status code 204)
     * @see PostsApi#deleteUserPostById
     */
    default ResponseEntity<Void> deleteUserPostById(UUID userId,
        UUID postId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts/{postId} : Get user&#39;s post by post id
     * Get users post by post id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @return OK (status code 200)
     * @see PostsApi#getUserPostByPostId
     */
    default ResponseEntity<Post> getUserPostByPostId(UUID userId,
        UUID postId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"images\" : [ null, null ], \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/posts : Get user&#39;s posts
     * List users post by id
     *
     * @param userId  (required)
     * @param page  (optional)
     * @param size  (optional)
     * @return OK (status code 200)
     * @see PostsApi#listUsersPostsByUserId
     */
    default ResponseEntity<PostsPage> listUsersPostsByUserId(UUID userId,
        Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 5, \"last\" : true, \"size\" : 1, \"numberOfElements\" : 5, \"totalPages\" : 0, \"sort\" : { \"unsorted\" : true, \"sorted\" : true, \"empty\" : true }, \"first\" : true, \"content\" : [ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"images\" : [ null, null ], \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"images\" : [ null, null ], \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ], \"totalElements\" : 6, \"empty\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /users/{userId}/posts/{postId} : Update post by id
     * Update users post by id
     *
     * @param userId  (required)
     * @param postId  (required)
     * @param updatePostRequest  (optional)
     * @return OK (status code 200)
     * @see PostsApi#updateUserPostById
     */
    default ResponseEntity<Post> updateUserPostById(UUID userId,
        UUID postId,
        UpdatePostRequest updatePostRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"images\" : [ null, null ], \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
