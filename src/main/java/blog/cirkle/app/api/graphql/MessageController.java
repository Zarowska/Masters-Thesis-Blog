package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.message.*;
import blog.cirkle.app.api.graphql.model.reaction.CreateReactionInput;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller("GraphQLMessageController")
public class MessageController {

	@QueryMapping
	public DialogInfoPage getDialogs(@Argument Integer page, @Argument Integer size, @Argument List<String> sort) {
		// Implement logic to retrieve all dialogs
		return null; // Replace with actual implementation
	}

	@QueryMapping
	public MessagePage getMessagesByUserId(@Argument String userId, @Argument Integer page, @Argument Integer size,
			@Argument List<String> sort) {
		// Implement logic to get messages exchanged with a user
		return null; // Replace with actual implementation
	}

	@MutationMapping
	public Message createMessage(@Argument String userId, @Argument CreateMessageInput input) {
		// Implement logic to send a message to a user
		return null; // Replace with actual implementation
	}

	@MutationMapping
	public Message updateMessage(@Argument String userId, @Argument String messageId,
			@Argument UpdateMessageInput input) {
		// Implement logic to update a message
		return null; // Replace with actual implementation
	}

	@MutationMapping
	public Boolean deleteMessage(@Argument String userId, @Argument String messageId) {
		// Implement logic to delete a message
		return true; // Replace with actual implementation
	}

	@MutationMapping
	public List<ReactionList> addReactionToMessage(@Argument String userId, @Argument String messageId,
			@Argument CreateReactionInput input) {
		// Implement logic to add a reaction to a message
		return null; // Replace with actual implementation
	}

	@MutationMapping
	public List<ReactionList> addReactionToMessageImage(@Argument String userId, @Argument String messageId,
			@Argument String imageId, @Argument CreateReactionInput input) {
		// Implement logic to add a reaction to a message image
		return null; // Replace with actual implementation
	}
}
