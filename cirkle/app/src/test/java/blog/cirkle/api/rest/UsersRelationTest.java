package blog.cirkle.api.rest;

import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.exception.ClientResponseException;
import blog.cirkle.domain.model.response.RelationDto;
import blog.cirkle.domain.model.response.RelationRequestDto;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UsersRelationTest extends AbstractApiTest {

	@Test
	void shouldNotAllowToFriendOrFollowSelf() {
		asVictor(client -> {

			assertThatThrownBy(() -> client.user(client.getId()).friend()).isInstanceOf(ClientResponseException.class)
					.hasMessage("You can't create relation with self!");

			assertThatThrownBy(() -> client.user(client.getId()).follow()).isInstanceOf(ClientResponseException.class)
					.hasMessage("You can't create relation with self!");

			assertThatThrownBy(() -> client.user(client.getId()).unfriend()).isInstanceOf(ClientResponseException.class)
					.hasMessageContaining("Relation of type FRIEND between").hasMessageContaining("not exists");

			assertThatThrownBy(() -> client.user(client.getId()).unfollow()).isInstanceOf(ClientResponseException.class)
					.hasMessageContaining("Relation of type FOLLOWER between").hasMessageContaining("not exists");

		});
	}

	@Test
	void shouldNotAllowSendRequestTwice() {
		asVictor(victor -> {
			asTrent(trent -> {
				trent.user(victor.getId()).friend();
				trent.user(victor.getId()).follow();

				assertThatThrownBy(() -> trent.user(victor.getId()).friend())
						.isInstanceOf(ClientResponseException.class)
						.hasMessageContaining("FRIEND relation request between").hasMessageContaining("already sent");

				assertThatThrownBy(() -> trent.user(victor.getId()).follow())
						.isInstanceOf(ClientResponseException.class).hasMessageContaining("FOLLOWED relation between")
						.hasMessageContaining("already exists");
			});
		});
	}

	@Test
	void shouldListFriendsAndFollowers() {
		asVictor(victor -> {
			asTrent(trent -> {
				asAlice(alice -> {
					asBob(bob -> {
						victor.user(trent.getId()).friend();

						List<RelationRequestDto> requests = trent.user().relationRequests(0, 100).getContent();

						Set<UUID> expectedIds = Set.of(alice.getId(), bob.getId(), victor.getId());
						List<UUID> initiatorsIds = requests.stream().map(it -> it.getUser().getId()).toList();
						assertThatCollection(initiatorsIds).hasSize(3).containsAll(expectedIds);

						List<RelationDto> trentFriendsBefore = trent.user(trent.getId()).friends(0, 100).getContent();

						assertThatCollection(trentFriendsBefore).isEmpty();

						List<RelationDto> trentFollowers = trent.user(trent.getId()).followers(0, 100).getContent();

						System.out.println(trentFriendsBefore);
						System.out.println(trentFollowers);

						// accept all requests

						requests.forEach(it -> trent.user().acceptRequest(it.getId()));

						List<RelationDto> trentFriendsAfter = trent.user(trent.getId()).friends(0, 100).getContent();

						Set<UUID> expectedFriendsIds = Set.of(alice.getId(), bob.getId(), victor.getId());
						List<UUID> trentFriendsId = trentFriendsAfter.stream().map(it -> it.getUser().getId()).toList();

						assertThatCollection(trentFriendsId).containsAll(expectedFriendsIds);
					});
				});
			});
		});
	}
}
