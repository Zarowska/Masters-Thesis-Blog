package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.DialogInfoDto;
import blog.cirkle.app.api.rest.model.MessageDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import java.util.Map;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

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
	Call<PaginatedResponse<DialogInfoDto>> getDialogs(@QueryMap Map<String, String> pageable);

	@GET("/messages/{userId}")
	Call<PaginatedResponse<MessageDto>> getMessagesByUserId(@Path("userId") UUID userId,
			@QueryMap Map<String, String> pageable);
}