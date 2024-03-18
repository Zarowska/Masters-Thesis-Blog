package com.zarowska.cirkle.api.rest.controller;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.response.user.User;
import com.zarowska.cirkle.api.rest.model.response.user.UserReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerTest extends AbstractTest {

    @Test
    void shouldGetCurrentUserReference() {
        ResponseEntity<UserReference> response = get(REST_API_ROOT_PATH + "/user", UserReference.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserReference actual = response.getBody();
        UserReference expected = new UserReference(actual.getId(), "Test User", "https://i.pravatar.cc/150?img=6");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetCurrentUserProfile() {
        ResponseEntity<User> response = get(REST_API_ROOT_PATH + "/user/profile", User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        User actual = response.getBody();
        User expected = new User(actual.getId(), "testuser@gmail.com", "Test User", "https://i.pravatar.cc/150?img=6");

        assertThat(actual).isEqualTo(expected);
    }

}