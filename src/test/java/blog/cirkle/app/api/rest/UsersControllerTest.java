package blog.cirkle.app.api.rest;

import static blog.cirkle.app.api.rest.TestUtils.twoBunniesPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UsersControllerTest extends AbstractApiTest {

	@Test
	void findAll() {
		asEve(eve -> {
			asAlice(alice -> {
				asBob(bob -> {
					PaginatedResponse<ParticipantDto> allUsers = eve.users.findAllUsers(Pageable.DEFAULT);
					List<UUID> allUserIds = allUsers.getContent().stream().map(ParticipantDto::getId).toList();
					assertTrue(allUserIds.contains(eve.getUserId()));
					assertTrue(allUserIds.contains(alice.getUserId()));
					assertTrue(allUserIds.contains(bob.getUserId()));
				});
			});
		});
	}

	@Test
	void findByUserId() {
		asEve(eve -> {
			ParticipantDto eveDto = eve.users.findUserById(eve.getUserId());
			assertEquals(eve.getUserId(), eveDto.getId());
			assertEquals("Eve", eveDto.getName());
		});
	}

	@Test
	void findProfileByUserId() {
		asEve(eve -> {
			UserProfileDto profile = eve.users.getUserProfileById(eve.getUserId());
			assertEquals("Eve", profile.getName());
		});
	}

	@Test
	void findPostsByUserId() {
		asEve(eve -> {
			PostDto post1 = twoBunniesPost(eve);
			PostDto post2 = twoBunniesPost(eve);
			asAlice(alice -> {
				PaginatedResponse<PostDto> posts = alice.users.listPostsByUserId(eve.getUserId(), Pageable.DEFAULT);
				assertEquals(List.of(post1, post2), posts.getContent());
			});

		});
	}

	@Test
	void follow() {
		asEve(eve -> {
			asAlice(alice -> {
				alice.users.followUser(eve.getUserId());

				PaginatedResponse<ParticipantDto> followers = eve.user.listFollowers(Pageable.DEFAULT);
				List<ParticipantDto> filtered = followers.getContent().stream()
						.filter(it -> it.getId().equals(alice.getUserId())).toList();
				assertEquals(1, filtered.size());
				assertEquals("Alice", filtered.get(0).getName());
			});
		});
	}

	@Test
	void unfollow() {
		asEve(eve -> {
			asAlice(alice -> {
				alice.users.followUser(eve.getUserId());

				PaginatedResponse<ParticipantDto> followers = eve.user.listFollowers(Pageable.DEFAULT);
				List<ParticipantDto> filtered = followers.getContent().stream()
						.filter(it -> it.getId().equals(alice.getUserId())).toList();
				assertEquals(1, filtered.size());
				assertEquals("Alice", filtered.get(0).getName());

				alice.users.unfollowUser(eve.getUserId());

				PaginatedResponse<ParticipantDto> followers2 = eve.user.listFollowers(Pageable.DEFAULT);
				assertTrue(followers2.getContent().isEmpty());

			});
		});
	}

	@Test
	void friend() {
		asAlice(alice -> {
			asEve(eve -> {
				alice.users.friendUser(eve.getUserId());
				PaginatedResponse<RequestDto> requests = eve.user.listUserRequests(Pageable.DEFAULT);
				Optional<RequestDto> aliceRequest = requests.getContent().stream()
						.filter(it -> it.getSender().getId().equals(alice.getUserId())).findFirst();
				assertTrue(aliceRequest.isPresent());
				eve.user.acceptParticipantRequest(aliceRequest.get().getId());

				PaginatedResponse<ParticipantDto> eveFriends = eve.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, eveFriends.getContent().size());
				assertEquals("Alice", eveFriends.getContent().get(0).getName());

				PaginatedResponse<ParticipantDto> aliceFriends = alice.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, aliceFriends.getContent().size());
				assertEquals("Eve", aliceFriends.getContent().get(0).getName());
			});
		});
	}

	@Test
	void unfriend() {
		asAlice(alice -> {
			asEve(eve -> {
				alice.users.friendUser(eve.getUserId());
				PaginatedResponse<RequestDto> requests = eve.user.listUserRequests(Pageable.DEFAULT);
				Optional<RequestDto> aliceRequest = requests.getContent().stream()
						.filter(it -> it.getSender().getId().equals(alice.getUserId())).findFirst();
				assertTrue(aliceRequest.isPresent());
				eve.user.acceptParticipantRequest(aliceRequest.get().getId());

				PaginatedResponse<ParticipantDto> eveFriends = eve.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, eveFriends.getContent().size());
				assertEquals("Alice", eveFriends.getContent().get(0).getName());

				PaginatedResponse<ParticipantDto> aliceFriends = alice.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, aliceFriends.getContent().size());
				assertEquals("Eve", aliceFriends.getContent().get(0).getName());

				alice.users.unfriendUser(eve.getUserId());

				PaginatedResponse<ParticipantDto> eveFriends2 = eve.user.listFriends(Pageable.DEFAULT);
				assertEquals(0, eveFriends2.getContent().size());

				PaginatedResponse<ParticipantDto> aliceFriends2 = alice.user.listFriends(Pageable.DEFAULT);
				assertEquals(0, aliceFriends2.getContent().size());
			});
		});
	}
}