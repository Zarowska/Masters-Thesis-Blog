package com.zarowska.cirkle.api.rest;

import java.io.File;
import com.zarowska.cirkle.api.model.FilePage;
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
 * A delegate to be called by the {@link ImagesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface ImagesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /images/{imageId} : Download image
     * Download image by id
     *
     * @param imageId  (required)
     * @param width  (optional)
     * @param height  (optional)
     * @return OK (status code 200)
     * @see ImagesApi#downloadImageById
     */
    default ResponseEntity<org.springframework.core.io.Resource> downloadImageById(UUID imageId,
        Integer width,
        Integer height) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/images/{imageId} : Get image info by id
     * List own image info by id
     *
     * @param imageId  (required)
     * @return OK (status code 200)
     * @see ImagesApi#getImageInfoById
     */
    default ResponseEntity<File> getImageInfoById(UUID imageId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"owner\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"size\" : 5, \"uploadedAt\" : \"2000-01-23T04:56:07.000+00:00\", \"mediaType\" : \"mediaType\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"url\" : \"https://openapi-generator.tech\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/images : List images
     * List own images
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return OK (status code 200)
     * @see ImagesApi#getImageInfoList
     */
    default ResponseEntity<FilePage> getImageInfoList(Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 5, \"last\" : true, \"size\" : 1, \"numberOfElements\" : 2, \"totalPages\" : 0, \"sort\" : { \"unsorted\" : true, \"sorted\" : true, \"empty\" : true }, \"first\" : true, \"content\" : [ { \"owner\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"size\" : 5, \"uploadedAt\" : \"2000-01-23T04:56:07.000+00:00\", \"mediaType\" : \"mediaType\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"url\" : \"https://openapi-generator.tech\" }, { \"owner\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"size\" : 5, \"uploadedAt\" : \"2000-01-23T04:56:07.000+00:00\", \"mediaType\" : \"mediaType\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"url\" : \"https://openapi-generator.tech\" } ], \"totalElements\" : 6, \"empty\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /user/images : Upload image
     * Upload new image
     *
     * @param fileName  (optional)
     * @return OK (status code 200)
     * @see ImagesApi#uploadImage
     */
    default ResponseEntity<Void> uploadImage(String fileName) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
