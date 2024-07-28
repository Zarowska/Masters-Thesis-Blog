package blog.cirkle.app.api.rest;

import static blog.cirkle.app.api.rest.TestUtils.twoBunniesPost;
import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.model.entity.ParticipantRequest;
import java.util.List;
import java.util.UUID;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class UserControllerTest extends AbstractApiTest {
	@Test
	void getCurrentUserInfo() {
		asEve(eve -> {
			ParticipantDto currentUserInfo = eve.user.getCurrentUserInfo();
			assertEquals("Eve", currentUserInfo.getName());
			assertNotNull(currentUserInfo.getId());
			assertNotNull(currentUserInfo.getAvatarUrl());
			assertFalse(currentUserInfo.getIsGroup());
			UUID imageId = extractImageID(currentUserInfo.getAvatarUrl());
			eve.images.getImageByID(imageId);
		});
	}

	@Test
	void listUserImages() {
		asEve(eve -> {
			PaginatedResponse<ImageDto> images = eve.user.listUserImages(Pageable.DEFAULT);
			assertEquals(2, images.getTotalElements());
		});

	}

	@Test
	void uploadImage() {
		asEve(eve -> {
			List.of("bunny.gif", "bunny.jpg", "bunny.png", "bunny-8.png", "bunny-transparent.png")
					.forEach(name -> eve.user.uploadImage(new ClassPathResource("/images/" + name)));
			PaginatedResponse<ImageDto> images = eve.user.listUserImages(Pageable.DEFAULT);
			assertEquals(7, images.getTotalElements());

			images.getContent().forEach(image -> {
				ResponseBody responseBody = eve.images.getImageByID(image.getId());
				assertNotNull(responseBody);
			});

			// should be accessible by other users
			asAlice(alice -> {
				images.getContent().forEach(image -> {
					ResponseBody responseBody = eve.images.getImageByID(image.getId());
					assertNotNull(responseBody);
				});
			});

		});
	}

	@Test
	void listUserPosts() {
		asEve(eve -> {
			PostDto post = eve.posts.createPost(CreatePostDto.builder().text("Simple post").build());
			assertEquals("Simple post", post.getText());
		});
	}

	@Test
	void listUserRequests() {
		asEve(eve -> {
			asAlice(alice -> {
				PaginatedResponse<RequestDto> eveRequestsBefore = eve.user.listUserRequests(Pageable.DEFAULT);
				assertTrue(eveRequestsBefore.isEmpty());

				alice.users.friendUser(eve.getUserId());
				PaginatedResponse<RequestDto> eveRequestsAfter = eve.user.listUserRequests(Pageable.DEFAULT);
				assertEquals(1, eveRequestsAfter.getContent().size());
				assertEquals("Alice", eveRequestsAfter.getContent().get(0).getSender().getName());
				assertEquals(ParticipantRequest.ParticipantRequestType.FRIEND,
						eveRequestsAfter.getContent().get(0).getType());
			});
		});
	}

	@Test
	void acceptParticipantRequest() {
		asEve(eve -> {
			asAlice(alice -> {
				alice.users.friendUser(eve.getUserId());
				PaginatedResponse<RequestDto> eveRequestsBefore = eve.user.listUserRequests(Pageable.DEFAULT);
				RequestDto request = eveRequestsBefore.getContent().get(0);
				eve.user.acceptParticipantRequest(request.getId());
				PaginatedResponse<RequestDto> eveRequestsAfter = eve.user.listUserRequests(Pageable.DEFAULT);
				assertTrue(eveRequestsAfter.getContent().isEmpty());
				PaginatedResponse<ParticipantDto> aliceFriends = alice.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, aliceFriends.getContent().size());
				PaginatedResponse<ParticipantDto> eveFriends = eve.user.listFriends(Pageable.DEFAULT);
				assertEquals(1, eveFriends.getContent().size());
			});
		});
	}

	@Test
	void rejectParticipantRequest() {
		asEve(eve -> {
			asAlice(alice -> {
				alice.users.friendUser(eve.getUserId());
				PaginatedResponse<RequestDto> eveRequestsBefore = eve.user.listUserRequests(Pageable.DEFAULT);
				RequestDto request = eveRequestsBefore.getContent().get(0);
				eve.user.rejectParticipantRequest(request.getId());
				PaginatedResponse<RequestDto> eveRequestsAfter = eve.user.listUserRequests(Pageable.DEFAULT);
				assertTrue(eveRequestsAfter.getContent().isEmpty());
			});
		});
	}

	@Test
	void feed() {
		asAlice(alice -> {
			asEve(eve -> {
				asBob(bob -> {
					bob.users.friendUser(alice.getUserId());
					eve.users.friendUser(alice.getUserId());
					List<RequestDto> aliceRequests = alice.user.listUserRequests(Pageable.DEFAULT).getContent();
					aliceRequests.forEach(req -> alice.user.acceptParticipantRequest(req.getId()));
					// bob and eve now friends of alice
					PostDto alicePost = twoBunniesPost(alice);
					PaginatedResponse<PostDto> bobsFeed = bob.user.getUserFeed(Pageable.DEFAULT);
					PaginatedResponse<PostDto> evesFeed = eve.user.getUserFeed(Pageable.DEFAULT);
					assertTrue(bobsFeed.getContent().contains(alicePost));
					assertTrue(evesFeed.getContent().contains(alicePost));
				});
			});
		});
	}
}