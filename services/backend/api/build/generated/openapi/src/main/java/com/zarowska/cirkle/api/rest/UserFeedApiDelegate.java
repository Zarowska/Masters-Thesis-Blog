package com.zarowska.cirkle.api.rest;

import com.zarowska.cirkle.api.model.ApiError;
import com.zarowska.cirkle.api.model.PostsPage;
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
 * A delegate to be called by the {@link UserFeedApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface UserFeedApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /user/feed : User&#39;s feed
     * Get current user feed
     *
     * @param page  (optional)
     * @param size  (optional)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     * @see UserFeedApi#getFeed
     */
    default ResponseEntity<PostsPage> getFeed(Integer page,
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

}
