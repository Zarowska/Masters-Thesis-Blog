package blog.cirkle.app.service;

import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.model.entity.Reactable;
import blog.cirkle.app.model.entity.Reaction;
import blog.cirkle.app.model.entity.User;
import java.util.Collection;

public interface ReactionService {
	Collection<Reaction> createReaction(User currentUser, Reactable reactable, CreateReactionDto request);
}
