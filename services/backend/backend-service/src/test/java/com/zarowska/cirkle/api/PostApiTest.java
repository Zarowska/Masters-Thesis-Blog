package com.zarowska.cirkle.api;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostApiTest extends AbstractTest {
    @Test
    void shouldCreatePost() throws Exception {
        context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {
            CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
                    .build();
            Optional<Post> newPost = ctx.getApi().posts().createPost(ctx.getUserId(), request);
            assertTrue(newPost.isPresent());

        });
    }

    @Test
    void nestedTest() throws Exception {
        context("Bob Marley", "bob@email", "http://avatar").apply(bobContest -> {
                    context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {

                        // bob send friendship request to max

                        bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId().toString());

                        // max checks incoming friendship requests

                        FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();


                        // max finds bob's friendship request
                        Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
                                .filter(it -> it.getOwner().getId() == bobContest.getUserId()).findFirst();

                        assertTrue(request.isPresent());


                        // max accepts bob's friendship request
                        maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId().toString());

                        // max checks his friends
                        Optional<UserPage> maxFriends = bobContest.getApi().relations()
                                .getUsersFriendsById(maxContext.getUserId().toString(), 0, 100);

                        assertTrue(maxFriends.isPresent());

                        // max found bob in his friendship request
                        Optional<@Valid User> bob = maxFriends.get().getContent()
                                .stream().filter(it -> it.getId() == bobContest.getUserId()).findFirst();

                        // bob now friend of max
                        assertTrue(bob.isPresent());

                    });
                }
        );
    }
}
