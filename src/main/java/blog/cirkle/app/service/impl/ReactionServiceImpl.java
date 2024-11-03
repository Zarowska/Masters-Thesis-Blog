package blog.cirkle.app.service.impl;

import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.model.entity.Reactable;
import blog.cirkle.app.model.entity.Reaction;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.ReactionRepository;
import blog.cirkle.app.service.ReactionService;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

	private final ReactionRepository reactionRepository;

	@Override
	public Collection<Reaction> createReaction(User currentUser, Reactable reactable, CreateReactionDto request) {
		List<Reaction> userReactions = reactable.getReactions().stream().filter(it -> it.getUser().equals(currentUser))
				.toList();
		reactable.getReactions().remove(userReactions);
		reactionRepository.deleteAll(userReactions);
		if (request.getValue() > 0) {
			Reaction reaction = Reaction.builder().value(request.getValue()).user(currentUser).build();
			Reaction newReaction = reactionRepository.save(reaction);
			reactable.getReactions().add(newReaction);
		}
		return reactable.getReactions();
	}
}
