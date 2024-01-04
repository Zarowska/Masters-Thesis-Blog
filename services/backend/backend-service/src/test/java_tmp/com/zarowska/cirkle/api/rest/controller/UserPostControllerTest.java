package com.zarowska.cirkle.api.rest.controller;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.request.CreatePostRequest;
import com.zarowska.cirkle.api.rest.model.response.post.Post;
import com.zarowska.cirkle.api.rest.model.response.post.PostListResponse;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserPostControllerTest extends AbstractTest {

    @Test
    void shouldCreatePost() {
        CreatePostRequest request = new CreatePostRequest("new post", Collections.emptyList());
        ResponseEntity<Post> response = post(REST_API_ROOT_PATH + "/user/posts", request, Post.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody().getId()).isNotNull();

        assertThat(response.getBody().getText()).isEqualTo(request.getText());

        // todo move to separate test class
        UUID authorId = response.getBody().getAuthor().getId();

        post(REST_API_ROOT_PATH + "/user/posts", new CreatePostRequest("new post1", Collections.emptyList()),
                Post.class);
        post(REST_API_ROOT_PATH + "/user/posts", new CreatePostRequest("new post2", Collections.emptyList()),
                Post.class);
        post(REST_API_ROOT_PATH + "/user/posts", new CreatePostRequest("new post3", Collections.emptyList()),
                Post.class);

        ResponseEntity<PostListResponse> postsResponse = get(REST_API_ROOT_PATH + "/users/{userId}/posts",
                PostListResponse.class, authorId);
        // todo replace with assertions
        postsResponse.getBody().getItems().forEach(post -> System.out.println(post));

    }
}