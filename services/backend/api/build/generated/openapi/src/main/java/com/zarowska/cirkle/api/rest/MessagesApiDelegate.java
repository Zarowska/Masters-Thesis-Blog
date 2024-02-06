package com.zarowska.cirkle.api.rest;

import com.zarowska.cirkle.api.model.ApiError;
import java.math.BigDecimal;
import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.api.model.MessageEventList;
import com.zarowska.cirkle.api.model.MessagePage;
import java.util.UUID;
import com.zarowska.cirkle.api.model.UpdateUserMessageRequest;
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
 * A delegate to be called by the {@link MessagesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
public interface MessagesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /user/messages/{messageId} : Delete message by id
     * Delete own message by id
     *
     * @param messageId  (required)
     * @return No Content (status code 204)
     * @see MessagesApi#deleteMessageById
     */
    default ResponseEntity<Void> deleteMessageById(UUID messageId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/messages/{messageId} : Get message by id
     * Get own message by id
     *
     * @param messageId  (required)
     * @return OK (status code 200)
     * @see MessagesApi#getMessageById
     */
    default ResponseEntity<Message> getMessageById(UUID messageId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"images\" : [ null, null ], \"from\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{userId}/messages
     * Get dialog with user
     *
     * @param userId  (required)
     * @param page  (optional)
     * @param size  (optional)
     * @return OK (status code 200)
     * @see MessagesApi#getMessagesByUserId
     */
    default ResponseEntity<MessagePage> getMessagesByUserId(UUID userId,
        BigDecimal page,
        BigDecimal size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 5, \"last\" : true, \"size\" : 1, \"numberOfElements\" : 5, \"totalPages\" : 0, \"sort\" : { \"unsorted\" : true, \"sorted\" : true, \"empty\" : true }, \"first\" : true, \"content\" : [ { \"images\" : [ null, null ], \"from\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }, { \"images\" : [ null, null ], \"from\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" } ], \"totalElements\" : 6, \"empty\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/messages/events : Unread messages events
     * Get message events 
     *
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Internal Server Error (status code 500)
     * @see MessagesApi#getUnreadMessageEvents
     */
    default ResponseEntity<MessageEventList> getUnreadMessageEvents() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"items\" : [ { \"lastAt\" : \"2000-01-23T04:56:07.000+00:00\", \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"count\" : 0 }, { \"lastAt\" : \"2000-01-23T04:56:07.000+00:00\", \"author\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"count\" : 0 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /user/messages/{messageId}/read : Mark message readed by id
     * Mark message as readed
     *
     * @param messageId  (required)
     * @return No Content (status code 204)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     * @see MessagesApi#markMessageReadById
     */
    default ResponseEntity<Void> markMessageReadById(UUID messageId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /users/{userId}/messages
     * Send user a message
     *
     * @param userId  (required)
     * @return OK (status code 200)
     * @see MessagesApi#sendMessageToUserById
     */
    default ResponseEntity<Void> sendMessageToUserById(UUID userId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /user/messages/{messageId} : Update message by id
     * Update own message by id
     *
     * @param messageId  (required)
     * @param updateUserMessageRequest  (optional)
     * @return OK (status code 200)
     * @see MessagesApi#updateMessageById
     */
    default ResponseEntity<Message> updateMessageById(UUID messageId,
        UpdateUserMessageRequest updateUserMessageRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"images\" : [ null, null ], \"from\" : { \"avatarUrl\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
