package com.zarowska.cirkle.api;

public class test {

	// for meeting zoom:
	// 1. yaml
	// 2. User, profile, feed
	// 3. File and Image
	// 4. API info
	// 5. Wrapper
	// 6. exception

	// 1 co to - ResponseEntity.ok?
	// @Override
	// public ResponseEntity<FriendshipRequestList> findAllFriendshipRequests() {
	// return ResponseEntity.ok(friendshipFacade.findAllFriendshipRequests());
	// }

	// 2. private final EntityManager em;

	// 3. public class WebConfig implements WebMvcConfigurer

	// 4. public FriendshipRequestList findAllFriendshipRequests() {
	// return doCall(() -> restTemplateWrapper.get(FriendshipRequestList.class,
	// "/user/requests")).get();
	// }
	// doCall(() ->

	// 5 . @PersistenceContext
	// private final EntityManager entityManager;
	// UserEntity currentUser =
	// entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

	// 6.
	// MessagePage().totalElements(messagePage.getTotalElements()).last(messagePage.isLast())
	// .first(messagePage.isFirst()).size(messagePage.getSize()).empty(messagePage.isEmpty())
	// .number(messagePage.getNumber()).numberOfElements(messagePage.getNumberOfElements())
	// .totalPages(messagePage.getTotalPages())
	// .content(messagePage.getContent().stream().map(messageMapper::toDto).toList());

}
