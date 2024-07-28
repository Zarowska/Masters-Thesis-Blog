package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.DialogInfoDto;
import blog.cirkle.app.api.rest.model.MessageDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessagesApi {
	@PUT("/messages/{userId}/messages/{messageId}")
	Call<MessageDto> updateMessageByUserId(@Path("userId") UUID userId, @Path("messageId") Long messageId,
			@Body UpdateMessageDto updateMessageDto);

	@DELETE("/messages/{userId}/messages/{messageId}")
	Call<Void> deleteMessageByUserId(@Path("userId") UUID userId, @Path("messageId") Long messageId);

	@POST("/messages/{userId}/messages")
	Call<MessageDto> createMessageByUserId(@Path("userId") UUID userId, @Body CreateMessageDto createMessageDto);

	@POST("/messages/{userId}/messages/{messageId}/reactions")
	Call<ReactionsDto> addReactionToMessage(@Path("userId") UUID userId, @Path("messageId") Long messageId,
			@Body CreateReactionDto createReactionDto);

	@POST("/messages/{userId}/messages/{messageId}/images/{imageId}/reactions")
	Call<ReactionsDto> addReactionToMessageImage(@Path("userId") UUID userId, @Path("messageId") Long messageId,
			@Path("imageId") String imageId, @Body CreateReactionDto createReactionDto);

	@GET("/messages")
	Call<PaginatedResponse<DialogInfoDto>> getDialogs(@Query("pageable") Pageable pageable);

	@GET("/messages/{userId}")
	Call<PaginatedResponse<MessageDto>> getMessagesByUserId(@Path("userId") UUID userId,
			@Query("pageable") Pageable pageable);
}